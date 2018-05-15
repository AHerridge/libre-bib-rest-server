# libre-bib
## Installation
### Make a directory to store the server files
```bash
mkdir libre-bib/
```
### Download libre-bib.jar
```bash
cd libre-bib/
```
Save [libre-bib.jar](/out/artifacts/libre-bib.jar) to libre-bib directory

## Usage
### Start server
> If there are no admins, the first authenticated user becomes admin.

> On first launch, this will create a directory called `persist` that contains all of the persistent data
```bash
java -jar libre-bib.jar
```
Goto http://localhost:8080/

### Stop server

Press `ctrl + c` in console
