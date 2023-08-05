import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SoalxsisFormService } from './soalxsis-form.service';
import { SoalxsisService } from '../service/soalxsis.service';
import { ISoalxsis } from '../soalxsis.model';

import { SoalxsisUpdateComponent } from './soalxsis-update.component';

describe('Soalxsis Management Update Component', () => {
  let comp: SoalxsisUpdateComponent;
  let fixture: ComponentFixture<SoalxsisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soalxsisFormService: SoalxsisFormService;
  let soalxsisService: SoalxsisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SoalxsisUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SoalxsisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoalxsisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soalxsisFormService = TestBed.inject(SoalxsisFormService);
    soalxsisService = TestBed.inject(SoalxsisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const soalxsis: ISoalxsis = { id: 456 };

      activatedRoute.data = of({ soalxsis });
      comp.ngOnInit();

      expect(comp.soalxsis).toEqual(soalxsis);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISoalxsis>>();
      const soalxsis = { id: 123 };
      jest.spyOn(soalxsisFormService, 'getSoalxsis').mockReturnValue(soalxsis);
      jest.spyOn(soalxsisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soalxsis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soalxsis }));
      saveSubject.complete();

      // THEN
      expect(soalxsisFormService.getSoalxsis).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(soalxsisService.update).toHaveBeenCalledWith(expect.objectContaining(soalxsis));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISoalxsis>>();
      const soalxsis = { id: 123 };
      jest.spyOn(soalxsisFormService, 'getSoalxsis').mockReturnValue({ id: null });
      jest.spyOn(soalxsisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soalxsis: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soalxsis }));
      saveSubject.complete();

      // THEN
      expect(soalxsisFormService.getSoalxsis).toHaveBeenCalled();
      expect(soalxsisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISoalxsis>>();
      const soalxsis = { id: 123 };
      jest.spyOn(soalxsisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soalxsis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soalxsisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
