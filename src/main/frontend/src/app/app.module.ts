import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import {
  MatToolbarModule,
  MatIconModule,
  MatListModule,
  MatCardModule,
  MatButtonModule
} from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';

import { NgMatSearchBarModule } from 'ng-mat-search-bar';


import { AppComponent } from './app.component';
import { LocationSearchService } from "../services/location-search.service";
import { DynamicScriptLoaderService } from "../services/dynamic-script-loader.service";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatButtonModule,
    FlexLayoutModule,
    NgMatSearchBarModule,
    BrowserAnimationsModule
  ],
  providers: [LocationSearchService, DynamicScriptLoaderService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
