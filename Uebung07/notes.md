# MOBPRO SW07

Die Angaben in der Aufgabenstellung funktionieren teilweise nur für Ionic 3. 

Diese Anleitung ist zum Lösen der Aufgabe 7 mit Ionic 4, aber dafür ohne WebStorm.

Ionic und Cordova installieren:

```
sudo npm install -g ionic cordova
```

in einem separaten Terminal die App starten und laufen lassen. Die App wird bei jeder Änderung automatisch neu kompiliert und im Browser aktualisiert.

```
ionic serve
```


## 1. Ionic-Projekt "MovieApp" aufsetzen

App erstellen mit Cordova Integration:

```
ionic start MovieApp sidemenu
cd MovieApp
ionic cordova platform 
```

Datei `src/app/home/home.page.html` anpassen:

```html
<ion-header>
  <ion-toolbar>
    <ion-buttons slot="start">
      <ion-menu-button></ion-menu-button>
    </ion-buttons>
    <ion-title>Movie App</ion-title>
  </ion-toolbar>
</ion-header>

<ion-content padding>
  <h3>Welcome Movie Fans!</h3>
  <p>This is a simple demo movie app.</p>
  <ion-button color="secondary" (click)="pushNativePage()">Show native Demo</ion-button>
</ion-content>

<ion-footer>
  <ion-toolbar>
    <ion-label align="right">Movie App / HSLU I</ion-label>
  </ion-toolbar>
</ion-footer>
```

## 2. Start-Seite, Skelett für weitere Seiten & Sidemenu

## Seiten erstellen

Neue Seiten erstellen für Search und Detail:

```
# Teilaufgabe 2
ionic generate page pages/search
ionic generate page pages/detail
```

### Projekt-Struktur anpassen

Verzeichnis `src/pages` erstellen und `home` und `list` verschieben nach `src/pages`.

### Routing

Datei `app-routing.module.ts` anpassen (Pfad in `loadChildren`):

```ts
import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './pages/home/home.module#HomePageModule'
  },
  {
    path: 'list',
    loadChildren: './pages/list/list.module#ListPageModule'
  },
  {
    path: 'search',
    loadChildren: './pages/search/search.module#SearchPageModule'
  },
  {
     path: 'detail',
     loadChildren: './pages/detail/detail.module#DetailPageModule'
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
```

## 3. Film-Suche

1. API-Key beantragen [omdbapi.com](http://www.omdbapi.com/apikey.aspx?__EVENTTARGET=freeAcct)
2. API-Key aktivieren per Link im Mail

```
# Example
curl "http://www.omdbapi.com/?apikey=37a7c7e4&plot=short&r=json&t=shrek"
```

### HTML

Datei `src/app/search/search.page.html` anpassen:

```html
<ion-header>
  <ion-toolbar>
    <ion-title>Movie Search</ion-title>
  </ion-toolbar>
</ion-header>

<ion-content padding>
  <h3>Search</h3>
  <p>Enter the movie title to search below and press the enter key or the search button.</p>
  <ion-input placeholder="shrek" [(ngModel)]="searchQuery"></ion-input>
  <ion-button color="secondary" (click)="doSearch()">Search</ion-button>
</ion-content>
```

### Interface für typsierten Zugriff

Datei `src/interfaces/Movie.ts` erstellen:

```ts
export interface Movie {
  Response: string;
  Title: string;
  Plot: string;
  Poster: string;
  Error: string;
  Country: string;
  Year: string;
  Director: string;
}
```

### HttpClient & NavController

Die in der Aufgabenstellung beschriebene Funktion `NavController.push()`, mit der man Daten an die nächste Seite weiterreichen kann existiert in Ionic 4 nicht mehr. 

Deshalb habe ich einen Service dafür erstellt (`src/app/services/MovieDetailService`), der das JSON an die Detail-Seite weitergibt:

```ts
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
```

In `src/app/search/app.module.ts` das Modul `HttpClientModule` bei den Import eintragen:

```ts
//...
@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(),
    AppRoutingModule
  ],
  //...
```

### Suche

Datei `src/app/pages/search/search.page.ts` anpassen:

```ts
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
        alert(movie.Error)
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
```

## 4. Film-Details

`src/pages/detail/detail/page.html`:

```html
<ion-header>
    <ion-toolbar>
    <ion-buttons slot="start">
      <ion-menu-button></ion-menu-button>
    </ion-buttons>
    <ion-title>Movie Details</ion-title>
  </ion-toolbar>
</ion-header>

<ion-content padding>
  <h1>{{movie.Title}}</h1>

<p><b>{{movie.Director}}</b></p>

<p><b>{{movie.Country}}, {{movie.Year}}</b></p>

<p>{{movie.Plot}}</p>
<img src="{{movie.Poster}}" width="300"/>
</ion-content>
```

`src/pages/detail/detail/page.ts`:

```ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/interfaces/Movie';
import { MovieDetailService } from 'src/app/services/MovieDetailService';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.page.html',
  styleUrls: ['./detail.page.scss'],
})
export class DetailPage implements OnInit {
  
  movie : Movie

  constructor(private actRoute: ActivatedRoute, private mds : MovieDetailService) { 
    this.movie = mds.getMovie();
  }
  ngOnInit() {}
}
```

## 5. Favoriten-Liste

Ordner `src/assets/movies` erstellen und Lieblingsfilme herunterladen:

```bash
mkdir src/assets/movies
wget "http://www.omdbapi.com/?apikey=37a7c7e4&plot=short&r=json&t=shrek" -O src/assets/movies/1.json
wget "http://www.omdbapi.com/?apikey=37a7c7e4&plot=short&r=json&t=clueless" -O src/assets/movies/2.json
wget "http://www.omdbapi.com/?apikey=37a7c7e4&plot=short&r=json&t=shrek 2" -O src/assets/movies/3.json
wget "http://www.omdbapi.com/?apikey=37a7c7e4&plot=short&r=json&t=life of brian" -O src/assets/movies/4.json
wget "http://www.omdbapi.com/?apikey=37a7c7e4&plot=short&r=json&t=shrek the third" -O src/assets/movies/5.json
```

## HTML & TS anpassen

`src/app/pages/list/list.page.html`:

```html
<ion-header>
  <ion-toolbar>
    <ion-buttons slot="start">
      <ion-menu-button></ion-menu-button>
    </ion-buttons>
    <ion-title>
      Favorite Movies
    </ion-title>
  </ion-toolbar>
</ion-header>

<ion-content>
  <ion-list>
    <ion-item *ngFor="let item of movies" (click)="showDetails(item.movie)">
      <ion-icon [name]="item.icon" slot="start"></ion-icon>
      
      <div class="item-note" slot="start">
          {{item.movie.Title}}
      </div>
    </ion-item>
  </ion-list>
</ion-content>
```

`src/app/pages/list.page.ts`:

```ts
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
    for (let i = 1; i <= 5; i++) {
      
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
```

## 6. Native App mit Cordova
  
Emulator starten:

```
~/Library/Android/sdk/emulator/emulator -avd Pixel_2_XL_API_28 &
```

Verfügbare AVDs auflisten:

```
~/Library/Android/sdk/emulator/emulator -list-avds
```

App auf dem Emulator laufen lassen:

```
ionic cordova run android --emulator
```

## 7. [OPTIONAL] Native APIs benutzten mit Cordova

Cordova Plugins für Battery-Status installieren:

```
ionic cordova plugin add cordova-plugin-battery-status
npm install @ionic-native/battery-status
```

Cordova Plugin für Network:

```
ionic cordova plugin add cordova-plugin-network-information
npm install @ionic-native/network
```

Neue Page `native` erstellen:

```
ionic generate page pages/native
```


