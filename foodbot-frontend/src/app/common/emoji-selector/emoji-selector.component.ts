import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {EmojiService} from '../../service/emoji.service';
import {Emoji, EmojiSection} from '../../model/emoji';
import {Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';

@Component({
	selector: 'app-emoji-selector',
	templateUrl: './emoji-selector.component.html',
	styleUrls: ['./emoji-selector.component.scss']
})
export class EmojiSelectorComponent implements OnInit {

	@Input() active = false;
	@Output() selectedEmoji = new EventEmitter<string>();

	emojiFilter = '';
	filterChanged: Subject<string> = new Subject<string>();
	emojiSections: EmojiSection[] = [];

	constructor(private emojiService: EmojiService) {
		this.filterChanged
			.pipe(
				debounceTime(250),
				distinctUntilChanged())
			.subscribe(data => this.emojiFilter = data);
	}

	ngOnInit(): void {
		this.emojiService.getEmojis()
			.subscribe(sections => this.emojiSections = sections);
	}

	reloadEmojiList(): void {
		this.emojiService.refreshEmojiList()
			.subscribe(sections => this.emojiSections = sections);
	}

	getFilteredEmoji(): EmojiSection[] {
		return this.emojiSections
			.map(emojiSection => {
				return {
					sectionName: emojiSection.sectionName,
					emojiList: emojiSection.emojiList
						.filter(emoji => this.emojiMeetsFilterCriterion(emoji))
				};
			})
			.filter(emojiSection => emojiSection.emojiList.length > 0);
	}

	private emojiMeetsFilterCriterion(emoji: Emoji): boolean {
		const emojiFilterLowercase = this.emojiFilter.toLowerCase();
		return emoji.name.toLowerCase().includes(emojiFilterLowercase)
			|| emoji.code.toLowerCase().includes(emojiFilterLowercase);
	}

	closeQuickView(): void {
		this.active = false;
		this.resetFilter();
	}

	selectEmoji(emojiCode: string): void {
		this.selectedEmoji.emit(emojiCode);
	}

	filterChange(filter: string): void {
		this.filterChanged.next(filter);
	}

	resetFilter() {
		this.emojiFilter = '';
	}
}
