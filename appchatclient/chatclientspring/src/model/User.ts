export class User {
    id: number;
    fullName: string;
    email: string;
    password: string;
    username: string;
    constructor(id: number, fullName: string, email: string, password: string, username: string) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.username = username;
    }

}