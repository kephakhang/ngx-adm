import { of as observableOf,  Observable } from 'rxjs'
import { Injectable } from '@angular/core'
import { Contacts, RecentUsers, UserData } from '../data/users'
import { AuthServiceProvider  } from '../../providers/auth-service/auth-service'

@Injectable()
export class UserService extends UserData {

  private time: Date = new Date
  private users = []
  private contacts = []
  private recentUsers = []

  constructor(public auth: AuthServiceProvider) {
    super()
  }

  getUsers(): Observable<any> {
    return observableOf(this.users)
  }

  getContacts(): Observable<Contacts[]> {
    return observableOf(this.contacts)
  }

  getRecentUsers(): Observable<RecentUsers[]> {
    return observableOf(this.recentUsers)
  }
}
