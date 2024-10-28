import {EmprestimoStatus} from "./emprestimoStatus";
import {Usuario} from "./usuario";
import {Livro} from "./livro";

export interface Emprestimo {
  id: number;
  version: number;
  usuario: Usuario;
  livro: Livro;
  dataEmprestimo: Date;
  dataDevolucao: Date;
  status: EmprestimoStatus;
}

export interface EmprestimoList {
  id: number;
  usuario: string;
  livro: string;
  dataEmprestimo: Date;
  dataDevolucao: Date;
}

export interface EmprestimoRetorno {
  id: number;
  dataDevolucao: Date;
  status: EmprestimoStatus;
}

