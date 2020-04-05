import {Component, ViewChild} from '@angular/core';
import {PromptComponent} from '../../../common/prompt/prompt.component';

@Component({
	selector: 'app-instant-message',
	templateUrl: './instant-message.component.html',
	styleUrls: ['./instant-message.component.css']
})
export class InstantMessageComponent {

	@ViewChild('messagePrompt') messagePrompt: PromptComponent;

	messageText: string;

	displayMessagePrompt() {
		this.messagePrompt.active = true;
	}

	onAnswerReceived(answer: boolean) {
		this.messageText = null;
		console.log(answer);
	}

}
