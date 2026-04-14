import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageEmprunt } from './emprunt';

describe('PageEmprunt', () => {
  let component: PageEmprunt;
  let fixture: ComponentFixture<PageEmprunt>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PageEmprunt],
    }).compileComponents();

    fixture = TestBed.createComponent(PageEmprunt);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
