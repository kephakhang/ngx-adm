export class TenantData {
    constructor(
        public id: string,
        public name: string,
        public type: number,
        public description: string,
        public countyCode: string,
        public hostUrl: string,
        public prefix: string,
        public hostname: string,
        public regDatetime: string,
        public modDatetime: string
    ) {}
}