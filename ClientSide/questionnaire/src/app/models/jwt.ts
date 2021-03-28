import { Role } from "./role";

export class Jwt {
    constructor(
        public token:string,
        public username:string,
        public role: Role
    ){}
}
