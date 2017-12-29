import { Injectable } from '@angular/core';
import { Reimbursement } from './reimbursement';

const reimbursements = [
    {
        'id': 1,
        'email': 'john@cena.com',
        'description': 'Trumpets are expensive',
        'amount': 10.25
    },
    {
        'id': 2,
        'email': 'gas@gas.gas',
        'description': 'I\'m going to step on the gas',
        'amount': 6.12
    },
    {
        'id': 3,
        'email': 'nickel@dime.com',
        'description': 'They really nickel and dime you',
        'amount': 0.15
    }
]

@Injectable()
export class ReimbursementService {
    getReimbursements(): Reimbursement[] {
        return reimbursements;
    }

    getReimbursementById(reimbursementId: number): Reimbursement {
        return reimbursements.find(r => r.id === reimbursementId);
    }
}