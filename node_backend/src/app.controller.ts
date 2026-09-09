import { Controller, Get } from '@nestjs/common';
import { AppService, type User } from './app.service.js';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    return this.appService.getHello();
  }

  @Get('users')
  getUsers(): Promise<User[]> {
    return this.appService.getUsers();
  }
}
