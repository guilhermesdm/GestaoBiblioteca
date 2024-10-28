export enum LivroCategoria {
  ROMANCE = 'ROMANCE',
  FANTASIA = 'FANTASIA',
  FICCAO = 'FICCAO',
  AVENTURA = 'AVENTURA',
  MISTERIO = 'MISTERIO',
  TERROR = 'TERROR',
  BIOGRAFIA = 'BIOGRAFIA',
  HISTORIA = 'HISTORIA',
  FILOSOFIA = 'FILOSOFIA',
  CIENCIA = 'CIENCIA',
  SOCIOLOGIA = 'SOCIOLOGIA',
  LITERATURA_BRASILEIRA = 'LITERATURA_BRASILEIRA',
  LITERATURA_ESTRANGEIRA = 'LITERATURA_ESTRANGEIRA',
  POESIA = 'POESIA',
  FICCAO_JUVENIL = 'FICCAO_JUVENIL',
  DESCONHECIDA = 'DESCONHECIDA',
}

const categoriaMap = {
  [LivroCategoria.ROMANCE]: 'Romance',
  [LivroCategoria.FANTASIA]: 'Fantasia',
  [LivroCategoria.FICCAO]: 'Ficção',
  [LivroCategoria.AVENTURA]: 'Aventura',
  [LivroCategoria.MISTERIO]: 'Mistério',
  [LivroCategoria.TERROR]: 'Terror',
  [LivroCategoria.BIOGRAFIA]: 'Biografia',
  [LivroCategoria.HISTORIA]: 'História',
  [LivroCategoria.FILOSOFIA]: 'Filosofia',
  [LivroCategoria.CIENCIA]: 'Ciência',
  [LivroCategoria.SOCIOLOGIA]: 'Sociologia',
  [LivroCategoria.LITERATURA_BRASILEIRA]: 'Literatura Brasileira',
  [LivroCategoria.LITERATURA_ESTRANGEIRA]: 'Literatura Estrangeira',
  [LivroCategoria.POESIA]: 'Poesia',
  [LivroCategoria.FICCAO_JUVENIL]: 'Ficção juvenil',
  [LivroCategoria.DESCONHECIDA]: 'DESCONHECIDA',
}

export function getNomeCategoria(categoria: LivroCategoria): string {
  return categoriaMap[categoria] || LivroCategoria.DESCONHECIDA;
}
