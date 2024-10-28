import {Component, Inject} from '@angular/core';
import {ErrorBean} from "../model/ErrorBean";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-error',
  templateUrl: './error-message.component.html',
  styleUrls: ['./error-message.component.scss']
})
export class ErrorMessageComponent {

  constructor(@Inject(MAT_DIALOG_DATA) protected errors: ErrorBean[]) {}

}
