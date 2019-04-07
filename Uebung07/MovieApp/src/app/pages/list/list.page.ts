import { Component, OnInit } from '@angular/core';
import { Movie } from 'src/app/interfaces/Movie';
import { NavController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';
import { MovieDetailService } from 'src/app/services/MovieDetailService';

@Component({
  selector: 'app-list',
  templateUrl: 'list.page.html',
  styleUrls: ['list.page.scss']
})
export class ListPage implements OnInit {
  private selectedItem: any;
  private icons = [
    'film'
  ];
  public movies: Array<{ id: number; movie: Movie; icon: string }> = [];
  constructor(
    public navCtl : NavController,
    public httpClient : HttpClient,
    private mds : MovieDetailService
  ) {
    for (let i = 1; i <= 8; i++) {
      
      let url = `../../../assets/movies/${i}.json`
      let movieJson = this.httpClient.get(url);
      movieJson.subscribe(data => {
        this.movies.push({
          id: i,
          movie: <Movie>data,
          icon: this.icons[0]
        });
      })
    }
  }

  showDetails(movie : Movie) {
    this.mds.setMovie(movie)
    this.navCtl.navigateForward('detail');
  }

  ngOnInit() {}
}
