import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EmojiSection} from '../model/emoji';

@Injectable({
	providedIn: 'root'
})
export class EmojiService {

	constructor(private http: HttpClient) {
	}

	getEmojis() {
		return this.http.get<EmojiSection[]>('http://localhost:4200/api/services/emoji');
	}

	refreshEmojiList() {
		return this.http.post<EmojiSection[]>('http://localhost:4200/api/services/emoji', null);
	}
}
