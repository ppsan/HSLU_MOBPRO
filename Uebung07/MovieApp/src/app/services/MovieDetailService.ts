import { Injectable } from "@angular/core";
import { Movie } from 'src/app/interfaces/Movie';

@Injectable({
  providedIn: "root"
})
export class MovieDetailService {
  private movie: Movie;
  constructor() {}

  public setMovie(movie) {
    this.movie = movie;
  }

  getMovie() {
    return this.movie;
  }
}