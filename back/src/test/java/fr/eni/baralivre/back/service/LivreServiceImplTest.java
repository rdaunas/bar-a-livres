package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.api.LivreController;
import fr.eni.baralivre.back.dto.CategorieDTO;
import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;
import fr.eni.baralivre.back.repository.CategorieRepository;
import fr.eni.baralivre.back.repository.LivreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivreServiceImplTest {

    @Mock
    private LivreRepository livreRepository;

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private LivreServiceImpl livreService;

    private Livre livreComplet;
    private LivreDTO livreDTOComplet;

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

        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setId(1);
        categorieDTO.setNom("Roman");

        livreDTOComplet = new LivreDTO();
        livreDTOComplet.setIsbn("978-3-16-148410-0");
        livreDTOComplet.setTitre("Le Seigneur des Anneaux");
        livreDTOComplet.setAuteur("Tolkien");
        livreDTOComplet.setCouverture("couverture.jpg");
        livreDTOComplet.setDescription("Une grande aventure");
        livreDTOComplet.setNbExemplaire(5);
        livreDTOComplet.setCategories(List.of(categorieDTO));
    }

    @Test
    @DisplayName("findAllLivre : tous les champs sont mappés dans le DTO")
    void findAllLivre_mappingComplet() {
        Page<Livre> pageMock = new PageImpl<>(List.of(livreComplet));
        when(livreRepository.findAll(any(Pageable.class))).thenReturn(pageMock);

        Page<LivreDTO> result = livreService.findAllLivre(0, 20);

        assertThat(result.getContent()).hasSize(1);
        LivreDTO dto = result.getContent().get(0);
        assertThat(dto.getIsbn()).isEqualTo("978-3-16-148410-0");
        assertThat(dto.getTitre()).isEqualTo("Le Seigneur des Anneaux");
        assertThat(dto.getAuteur()).isEqualTo("Tolkien");
        assertThat(dto.getCouverture()).isEqualTo("couverture.jpg");
        assertThat(dto.getDescription()).isEqualTo("Une grande aventure");
        assertThat(dto.getNbExemplaire()).isEqualTo(5);
        assertThat(dto.isActive()).isTrue();
    }

    @Test
    @DisplayName("findAllLivre : la description null est conservée")
    void findAllLivre_descriptionNull_resteNull() {
        livreComplet.setDescription(null);
        Page<Livre> pageMock = new PageImpl<>(List.of(livreComplet));
        when(livreRepository.findAll(any(Pageable.class))).thenReturn(pageMock);

        Page<LivreDTO> result = livreService.findAllLivre(0, 20);

        assertThat(result.getContent().get(0).getDescription()).isNull();
    }

    @Test
    @DisplayName("findAllLivre : les catégories sont mappées dans le DTO")
    void findAllLivre_categoriesMappees() {
        Page<Livre> pageMock = new PageImpl<>(List.of(livreComplet));
        when(livreRepository.findAll(any(Pageable.class))).thenReturn(pageMock);

        Page<LivreDTO> result = livreService.findAllLivre(0, 20);

        assertThat(result.getContent().get(0).getCategories())
                .hasSize(1)
                .anyMatch(c -> c.getNom().equals("Roman") && c.getId() == 1);
    }

    @Test
    @DisplayName("ajouterLivre : livre null → RuntimeException")
    void ajouterLivre_livreNull_throwsException() {
        assertThatThrownBy(() -> livreService.ajouterLivre(null))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Les informations du livre sont obligatoires");
    }

    @Test
    @DisplayName("ajouterLivre : livre valide → save appelé et DTO retourné")
    void ajouterLivre_livreValide_saveAppele() {

        when(categorieRepository.findAllById(any())).thenReturn(List.of(
                livreComplet.getCategories().iterator().next()
        ));
        when(livreRepository.save(any(Livre.class))).thenReturn(livreComplet);

        LivreDTO result = livreService.ajouterLivre(livreDTOComplet);

        verify(livreRepository, times(1)).save(any(Livre.class));
        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Le Seigneur des Anneaux");
    }

    @Test
    @DisplayName("ajouterLivre : dateAjout est renseignée automatiquement")
    void ajouterLivre_dateAjoutRenseignee() {

        when(categorieRepository.findAllById(any())).thenReturn(List.of(
                livreComplet.getCategories().iterator().next()
        ));
        when(livreRepository.save(any(Livre.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LivreDTO result = livreService.ajouterLivre(livreDTOComplet);

        assertThat(result.getDateAjout()).isNotNull();
        assertThat(result.getDateAjout()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("update : livre null → RuntimeException")
    void update_livreNull_throwsException() {
        assertThatThrownBy(() -> livreService.update(null))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Les informations du livre sont obligatoires");
    }

    @Test
    @DisplayName("update : ISBN inexistant → RuntimeException")
    void update_isbnInexistant_throwsException() {
        when(livreRepository.findById("ISBN-INCONNU")).thenReturn(Optional.empty());
        livreDTOComplet.setIsbn("ISBN-INCONNU");

        assertThatThrownBy(() -> livreService.update(livreDTOComplet))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("n'existe pas");
    }

    @Test
    @DisplayName("update : livre valide → save appelé et DTO retourné")
    void update_livreValide_saveAppele() {
        when(livreRepository.findById("978-3-16-148410-0")).thenReturn(Optional.of(livreComplet));

        when(categorieRepository.findAllById(any())).thenReturn(List.of(
                livreComplet.getCategories().iterator().next()
        ));
        when(livreRepository.save(any(Livre.class))).thenReturn(livreComplet);

        LivreDTO result = livreService.update(livreDTOComplet);

        verify(livreRepository, times(1)).save(any(Livre.class));
        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Le Seigneur des Anneaux");
    }

    @Test
    @DisplayName("update : dateAjout et isActive ne sont pas écrasés")
    void update_dateAjoutEtIsActiveConserves() {
        LocalDateTime dateOrigine = LocalDateTime.of(2024, 1, 1, 0, 0);
        livreComplet.setDateAjout(dateOrigine);
        livreComplet.setActive(false);

        when(livreRepository.findById("978-3-16-148410-0")).thenReturn(Optional.of(livreComplet));

        when(categorieRepository.findAllById(any())).thenReturn(List.of(
                livreComplet.getCategories().iterator().next()
        ));
        when(livreRepository.save(any(Livre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LivreDTO result = livreService.update(livreDTOComplet);

        assertThat(result.getDateAjout()).isEqualTo(dateOrigine);
        assertThat(result.isActive()).isFalse();
    }

    @Test
    @DisplayName("retirerLivre : ISBN null → RuntimeException")
    void retirerLivre_isbnNull_throwsException() {
        assertThatThrownBy(() -> livreService.retirerLivre(null))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("L'ISBN ne peut pas être null.");
    }

    @Test
    @DisplayName("retirerLivre : ISBN inexistant → RuntimeException")
    void retirerLivre_isbnInexistant_throwsException() {
        when(livreRepository.findById("ISBN-INCONNU")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> livreService.retirerLivre("ISBN-INCONNU"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ce livre n'existe pas.");
    }

    @Test
    @DisplayName("retirerLivre : ISBN valide → isActive passe à false")
    void retirerLivre_isbnValide_isActiveSetFalse() {
        when(livreRepository.findById("978-3-16-148410-0")).thenReturn(Optional.of(livreComplet));
        when(livreRepository.save(any(Livre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LivreDTO result = livreService.retirerLivre("978-3-16-148410-0");

        assertThat(result.isActive()).isFalse();
        verify(livreRepository, times(1)).save(any(Livre.class));
    }
}