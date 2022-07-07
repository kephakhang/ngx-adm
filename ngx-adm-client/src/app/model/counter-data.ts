export class CounterData {
    constructor(
        public id: string,
        public tenantId: string,
        public terminalId: string,
        public version: number,
        public status: number,
        public regDatetime: string,
        public modDatetime: string
    ) {}
}