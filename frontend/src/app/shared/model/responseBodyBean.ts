// similar ao response body mas traz somente o bean
export interface ResponseBodyBean<T> {
  message: string;
  body: T;
}
