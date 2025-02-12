import { TestBed } from '@angular/core/testing';

import { EnviaFormulárioService } from './envia-formulário.service';

describe('EnviaFormulárioService', () => {
  let service: EnviaFormulárioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnviaFormulárioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
