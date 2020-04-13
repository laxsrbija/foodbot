import {Component, OnInit} from '@angular/core';
import {WeeklyMenuService} from '../../service/weekly-menu.service';
import {WeeklyMenu} from '../../model/weekly-menu';

@Component({
	selector: 'app-weekly-menu',
	templateUrl: './weekly-menu.component.html',
	styleUrls: ['./weekly-menu.component.css']
})
export class WeeklyMenuComponent implements OnInit {

	weeklyMenu: WeeklyMenu[];

	constructor(private weeklyMenuService: WeeklyMenuService) {
	}

	ngOnInit(): void {
		this.weeklyMenuService.getWeeklyMenu()
			.subscribe(data => this.weeklyMenu = data);
	}

}
