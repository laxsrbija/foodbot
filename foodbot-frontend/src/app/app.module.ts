import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import { NavbarComponent } from './core/navbar/navbar.component';
import { HomeComponent } from './core/home/home.component';
import { InstantMessageComponent } from './core/home/instant-message/instant-message.component';
import { TodaysMenuComponent } from './core/home/todays-menu/todays-menu.component';
import { PromptComponent } from './common/prompt/prompt.component';
import {FormsModule} from '@angular/forms';

@NgModule({
	declarations: [
		AppComponent,
		NavbarComponent,
		HomeComponent,
		InstantMessageComponent,
		TodaysMenuComponent,
		PromptComponent
	],
	imports: [
		BrowserModule,
		FormsModule
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule {
}
