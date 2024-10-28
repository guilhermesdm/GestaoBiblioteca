import {LivroCategoria} from "./LivroCategoria";

export enum EmprestimoStatus {
  INDISPONIVEL = "INDISPONIVEL",
  DISPONIVEL = "DISPONIVEL"
}

const categoriaMap = {
  [EmprestimoStatus.INDISPONIVEL]: 'Indisponível',
  [EmprestimoStatus.DISPONIVEL]: 'Disponível'
}

export function getNomeStatus(status: EmprestimoStatus): string {
  return categoriaMap[status];
}
