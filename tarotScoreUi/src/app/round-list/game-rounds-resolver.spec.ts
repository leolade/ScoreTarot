import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { gameRoundsResolver } from './game-rounds-resolver';

describe('gameRoundsResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => gameRoundsResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
