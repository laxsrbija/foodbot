import {Component, ViewChild} from '@angular/core';
import {PromptComponent} from '../../../common/prompt/prompt.component';
import {EmojiSelectorComponent} from '../../../common/emoji-selector/emoji-selector.component';

@Component({
	selector: 'app-instant-message',
	templateUrl: './instant-message.component.html',
	styleUrls: ['./instant-message.component.css']
})
export class InstantMessageComponent {

	@ViewChild('messagePrompt') messagePrompt: PromptComponent;
	@ViewChild('emojiSelector') emojiSelector: EmojiSelectorComponent;

	messageText = '';

	displayMessagePrompt() {
		this.messagePrompt.active = true;
	}

	onAnswerReceived(answer: boolean) {
		this.messageText = null;
		console.log(answer);
	}

	onSelectedEmoji(emojiCode: string) {
		this.messageText = this.messageText.concat(' ' + emojiCode);
	}

	toggleEmojiSelector() {
		this.emojiSelector.active = true;
	}
}
