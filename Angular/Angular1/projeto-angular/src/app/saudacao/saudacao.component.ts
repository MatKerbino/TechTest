import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-saudacao',
  imports: [],
  templateUrl: './saudacao.component.html',
  styleUrl: './saudacao.component.css'
})
export class SaudacaoComponent implements OnInit{
  mensagem: string = 'Olá, bem-vindo ao Angular!';
  
  constructor() { }

  ngOnInit(): void {
  }
}
