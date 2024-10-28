import {LivroCategoria} from "./LivroCategoria";

export interface Livro {
  id: number;
  version: number;
  titulo: string;
  autor: string;
  isbn: string;
  dataPublicacao: Date;
  categoria: LivroCategoria;
  googleBooksId: string;
}

export interface LivroInfo {
  description: string;
}
