import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
	selector: 'app-prompt',
	templateUrl: './prompt.component.html'
})
export class PromptComponent {

	@Input() title = 'Are you sure?';
	@Input() content: string;
	@Input() active = false;

	@Output() answer = new EventEmitter<boolean>();

	close(userAnswer: boolean) {
		this.active = false;
		this.answer.emit(userAnswer);
	}

}
