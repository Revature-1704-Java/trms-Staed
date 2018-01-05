export class Reimb {
    id: number;
    email: string;
    state: number;
    cost: number;
    date: string;
    last: string;

    constructor(id: number, email: string, state: number, cost: number, eventDate: string, lastReview: string) {
        this.id = id;
        this.email = email;
        this.state = state;
        this.cost = cost;
        this.date = eventDate;
        this.last = lastReview;
    }

    toString(): String {
        return 'id: ' + this.id + ', email: ' + this.email + ', state: '
            + this.state + ', cost: ' + this.cost + ', event date: '
            + this.date + ', last reviewed: ' + this.last;
    }
}
