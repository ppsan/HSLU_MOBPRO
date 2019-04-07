import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';
import { Movie } from 'src/app/interfaces/Movie'
import { MovieDetailService } from 'src/app/services/MovieDetailService';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
})
export class SearchPage implements OnInit {

  constructor(
    public navCtl : NavController,
    public httpClient : HttpClient,
    private mds : MovieDetailService  
  ) {}

  ngOnInit() {}
 
  searchQuery : String
  
  doSearch() : void {
    let apikey : String = "37a7c7e4"
    let url = `http://www.omdbapi.com/?apikey=${apikey}&plot=short&r=json&t=${this.searchQuery}`
    console.log(url)
    let movieJson = this.httpClient.get(url);
    movieJson.subscribe(data => {
      let movie: Movie = <Movie>data;
      if (movie.Response == 'True') {
        this.mds.setMovie(movie)
        this.navCtl.navigateForward('detail');
      } else {
        //this.presentAlert(movie.Error)
        //alert(movie.Error)
      }
    })
  }

  // not working, so just use normal js alert
  async presentAlert(msg) {
    const alertController = document.querySelector('ion-alert-controller');
    await alertController.componentOnReady();
  
    const alert = await alertController.create({
      header: 'Alert',
      subHeader: 'Subtitle',
      message: 'This is an alert message. ' + msg,
      buttons: ['OK']
    });
    return await alert.present();
  }
}

