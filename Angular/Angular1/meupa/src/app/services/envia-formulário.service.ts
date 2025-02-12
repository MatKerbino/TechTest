import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EnviaFormularioService {

  constructor() { }

  enviaInformacaoParaBackend (info: string) {
    // Simula o envio do formulário para o backend
    console.log('Informações enviadas para o backend:', info);
    // Implemente a lógica de envio real aqui
    // Por exemplo, enviar os dados ao servidor usando HTTP, WebSockets, etc.
    // Retornar um Observable ou Promise com o resultado do envio
    return Promise.resolve(true); // Simula sucesso no envio
  }
}
