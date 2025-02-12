import { NgModule } from '@angular/core';
import { AppComponent } from '../app.component';
import { SaudacaoComponent } from '../saudacao/saudacao.component';
import { BrowserModule } from '@angular/platform-browser';



@NgModule({
  imports: [
    BrowserModule,
    AppComponent,
    SaudacaoComponent
  ],
  providers: [],
})
export class AppModuleModule { }
