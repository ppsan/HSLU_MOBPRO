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

  ngOnInit() {
  }

}
