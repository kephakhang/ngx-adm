export class UserData {
    constructor(
        public id: string,
        public tenantId: string,
        public name: string,
        public email: string,
        public mobile: string,
        public level: number,
        public regDatetime: string,
        public modDatetime: string
    ) {}
}