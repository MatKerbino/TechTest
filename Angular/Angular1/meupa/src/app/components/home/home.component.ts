import { Component, EventEmitter, Inject, input, Input, Output } from '@angular/core';
import { EnviaFormularioService} from '../../services/envia-formulário.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  private enviaFormularioService = Inject(EnviaFormularioService);
  meuBoleano = false;
  button = "amarilo";
  ariaArio = "ola ola ola";
  estranho = false;
  listOfItems = [{id : "list", name : "listOfItems"},{id : "Of", name: "Cebutinha"}]

  @Input() minhaPropsDeFora!: string;
  @Input("name") arroba!: string;

  @Output() emitindoValorName = new EventEmitter<string>();

  atualizarBoleano(valor: boolean) {
    this.meuBoleano = valor;
  }

  morte(){
    console.log('vosse morriu'); 
  }

  submit(valor: any) {
    console.log('ENTAO TOME');
    for (let i = 0; i < 100; i++){
      console.log('tomi');
    }
    this.emitindoValorName.emit(valor);
    this.enviaFormularioService.enviaInformacaoParaBackend(this.button);
  }
}
