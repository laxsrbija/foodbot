import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WeeklyMenu} from '../model/weekly-menu';

@Injectable({
	providedIn: 'root'
})
export class WeeklyMenuService {

	constructor(private http: HttpClient) {
	}

	getWeeklyMenu() {
		return this.http.get<WeeklyMenu[]>('http://localhost:4200/api/services/menu');
	}
}
