export class TerminalData {
    constructor(
        public id: string,
        public tenantId: string,
        public version: number,
        public status: number,  
        public ip: string,
        public regDatetime: string,
        public modDatetime: string
    ) {}
}