package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;
import fr.eni.baralivre.back.repository.LivreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivreServiceImplTest {

    @Mock
    private LivreRepository livreRepository;

    @InjectMocks
    private LivreServiceImpl livreService;

    private Livre livreComplet;

    @BeforeEach
    void setUp() {
        Categorie categorie = new Categorie();
        categorie.setId(1);
        categorie.setNom("Roman");

        livreComplet = Livre.builder()
                .isbn("978-3-16-148410-0")
                .titre("Le Seigneur des Anneaux")
                .auteur("Tolkien")
                .couverture("couverture.jpg")
                .description("Une grande aventure")
                .nbExemplaire(5)
                .dateAjout(LocalDateTime.now())
                .isActive(true)
                .categories(Set.of(categorie))
                .build();
    }

    @Test
    @DisplayName("findAllLivre : tous les champs sont mappés dans le DTO")
    void findAllLivre_mappingComplet() {
        when(livreRepository.findAll()).thenReturn(List.of(livreComplet));

        List<LivreDTO> result = livreService.findAllLivre();

        assertThat(result).hasSize(1);
        LivreDTO dto = result.get(0);

        assertThat(dto.getIsbn()).isEqualTo("978-3-16-148410-0");
        assertThat(dto.getTitre()).isEqualTo("Le Seigneur des Anneaux");
        assertThat(dto.getAuteur()).isEqualTo("Tolkien");
        assertThat(dto.getCouverture()).isEqualTo("couverture.jpg");
        assertThat(dto.getDescription()).isEqualTo("Une grande aventure"); // le bug original
        assertThat(dto.getNbExemplaire()).isEqualTo(5);
        assertThat(dto.isActive()).isTrue();
    }

    @Test
    @DisplayName("findAllLivre : la description null est conservée (pas remplacée)")
    void findAllLivre_descriptionNull_resteNull() {
        livreComplet.setDescription(null);
        when(livreRepository.findAll()).thenReturn(List.of(livreComplet));

        List<LivreDTO> result = livreService.findAllLivre();

        assertThat(result.get(0).getDescription()).isNull();
    }

    @Test
    @DisplayName("findAllLivre : les catégories sont mappées dans le DTO")
    void findAllLivre_categoriesMappees() {
        when(livreRepository.findAll()).thenReturn(List.of(livreComplet));

        List<LivreDTO> result = livreService.findAllLivre();

        assertThat(result.get(0).getCategories())
                .hasSize(1)
                .anyMatch(c -> c.getNom().equals("Roman") && c.getId() == 1);
    }

    @Test
    @DisplayName("findAllLivre : categories null → liste null dans le DTO (pas de NullPointerException)")
    void findAllLivre_categoriesNull_pasDeNPE() {
        livreComplet.setCategories(null);
        when(livreRepository.findAll()).thenReturn(List.of(livreComplet));

        assertThatCode(() -> livreService.findAllLivre()).doesNotThrowAnyException();

        List<LivreDTO> result = livreService.findAllLivre();
        assertThat(result.get(0).getCategories()).isNull();
    }

    @Test
    @DisplayName("findAllLivre : liste vide retournée par le repo → liste vide")
    void findAllLivre_listeVide() {
        when(livreRepository.findAll()).thenReturn(List.of());

        List<LivreDTO> result = livreService.findAllLivre();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("ajouterLivre : livre null → RuntimeException")
    void ajouterLivre_livreNull_throwsException() {
        assertThatThrownBy(() -> livreService.ajouterLivre(null))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Le livre est obligatoire");
    }

    @Test
    @DisplayName("ajouterLivre : livre valide → save appelé et livre retourné")
    void ajouterLivre_livreValide_saveAppele() {
        when(livreRepository.save(any(Livre.class))).thenReturn(livreComplet);

        Livre result = livreService.ajouterLivre(livreComplet);

        verify(livreRepository, times(1)).save(any(Livre.class));
        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Le Seigneur des Anneaux");
    }

    @Test
    @DisplayName("ajouterLivre : dateAjout est renseignée automatiquement")
    void ajouterLivre_dateAjoutRenseignee() {
        when(livreRepository.save(any(Livre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Livre result = livreService.ajouterLivre(livreComplet);

        assertThat(result.getDateAjout()).isNotNull();
        assertThat(result.getDateAjout()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("ajouterLivre : exception du repo → RuntimeException avec le titre")
    void ajouterLivre_repoException_wrappee() {
        when(livreRepository.save(any(Livre.class))).thenThrow(new RuntimeException("DB error"));

        assertThatThrownBy(() -> livreService.ajouterLivre(livreComplet))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Impossible de sauver")
                .hasMessageContaining("Le Seigneur des Anneaux");
    }
}