import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import * as XLSX from 'xlsx';

interface IUser {
  firstname: string;
  lastname: string;
  country: string;
  age: number;
  telephone: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  private apiUrl: string = 'http://localhost:8080/api/v1/users';

  public title = 'client';
  public users: IUser[] = [];
  public successfulUpload: boolean = false;
  public successfulSavedInDB: boolean = false;

  constructor(private http: HttpClient) {}

  public loadExcelFile(files: any) {
    const file = files.item(0);

    if (file !== null) {
      const reader = new FileReader();

      reader.onload = () => {
        const data = reader.result as string;
        const workbook = XLSX.read(data, { type: 'binary' });
        workbook.SheetNames.forEach(
          ((name: string) => {
            let jsonRawData = XLSX.utils.sheet_to_json(workbook.Sheets[name]);
            this.users = this.mapData(jsonRawData);

            // todo: improve validation
            this.successfulUpload = true;
          }).bind(this),
          this
        );
      };

      reader.onerror = (ex) => {
        console.log(ex);
      };
      reader.readAsBinaryString(file);
    }
  }

  public saveUsers(): void {
    this.http.post(this.apiUrl, this.users).subscribe();
    this.successfulUpload = false;
    this.successfulSavedInDB = true;
  }

  public onNewImport(): void {
    this.successfulSavedInDB = false;
    this.users = [];
  }

  private mapData(rawUsersData: any[]): IUser[] {
    return rawUsersData.map((rawUser) => ({
      firstname: rawUser['First Name'],
      lastname: rawUser['Last Name'],
      country: rawUser.Country,
      age: rawUser.Age,
      telephone: rawUser.Telephone,
    }));
  }
}
