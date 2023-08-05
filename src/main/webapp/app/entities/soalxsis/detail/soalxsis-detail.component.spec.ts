import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoalxsisDetailComponent } from './soalxsis-detail.component';

describe('Soalxsis Management Detail Component', () => {
  let comp: SoalxsisDetailComponent;
  let fixture: ComponentFixture<SoalxsisDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SoalxsisDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ soalxsis: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SoalxsisDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SoalxsisDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load soalxsis on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.soalxsis).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
