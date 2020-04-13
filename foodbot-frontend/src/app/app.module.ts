import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import { NavbarComponent } from './core/navbar/navbar.component';
import { HomeComponent } from './core/home/home.component';
import { InstantMessageComponent } from './core/home/instant-message/instant-message.component';
import { TodaysMenuComponent } from './core/home/todays-menu/todays-menu.component';
import { PromptComponent } from './common/prompt/prompt.component';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import { WeeklyMenuComponent } from './core/weekly-menu/weekly-menu.component';

const appRoutes: Routes = [
	{ path: '', component: HomeComponent },
	{ path: 'menu', component: WeeklyMenuComponent }
];

@NgModule({
	declarations: [
		AppComponent,
		NavbarComponent,
		HomeComponent,
		InstantMessageComponent,
		TodaysMenuComponent,
		PromptComponent,
		WeeklyMenuComponent
	],
	imports: [
		BrowserModule,
		FormsModule,
		RouterModule.forRoot(appRoutes)
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule {
}
