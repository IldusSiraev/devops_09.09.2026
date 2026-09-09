import { Injectable } from '@nestjs/common';
import { createConnection } from 'mysql2/promise';
import type { RowDataPacket } from 'mysql2';

export type User = {
  id: number;
  name: string;
};

interface UserRow extends User, RowDataPacket {}

@Injectable()
export class AppService {
  getHello(): string {
    return 'Hello World!';
  }

  async getUsers(): Promise<User[]> {
    const connection = await createConnection({
      host: process.env.DB_HOST ?? 'db',
      port: Number(process.env.DB_PORT ?? 3306),
      user: process.env.DB_USER ?? 'root',
      password: process.env.DB_PASSWORD ?? 'password',
      database: process.env.DB_NAME ?? 'testdb',
    });

    try {
      const [users] = await connection.query<UserRow[]>(
        'SELECT id, name FROM users',
      );
      return users;
    } finally {
      await connection.end();
    }
  }
}
