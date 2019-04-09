import { Component, OnInit } from '@angular/core';
import { BatteryStatus } from '@ionic-native/battery-status/ngx';

@Component({
  selector: 'app-native',
  templateUrl: './native.page.html',
  styleUrls: ['./native.page.scss'],
})

export class NativePage implements OnInit {

  level : number
  isPlugged : boolean

  constructor(private bs : BatteryStatus) {
    // watch change in battery status
      const subscription = this.bs.onChange().subscribe(status => {
      console.log(status.level, status.isPlugged);
      this.level = status.level;
      this.isPlugged = status.isPlugged;
    });
   }


  ngOnInit() { }

}
