# SoftwareTest2
软件测试－电信业务

## 数据库 sqlite
### mobileDB.db
- 表: clientInfo
  ```sql
    CREATE TABLE clientInfo(
    phoneNumber TEXT PRIMARY KEY NOT NULL,
    owedBeforeYear REAL NOT NULL,
    owedThisYear REAL NOT NULL,
    timeOutCount REAL NOT NULL);
  ```

- 表: clientCommunicationTime
```sql
  CREATE TABLE clientCommunicationTime(
  phoneNumber TEXT PRIMARY KEY NOT NULL,
  communicationTime REAL NOT NULL);
```

### bankDB.db
```sql
  CREATE TABLE account(
  accountId INTEGER PRIMARY KEY NOT NULL,
  accountPw TEXT NOT NULL,
  accountBalance INTEGER NOT NULL);
```
