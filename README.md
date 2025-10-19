# SEDS 514 - PROJECT 1

## DUE DATE

26.10.2025, 23:55

## PROGRESS SUMMARY

**Completed:**
- ✅ Maven project setup with JUnit 4 and JaCoCo
- ✅ NextDate program implementation (year: 1812-2100)
- ✅ Boundary value testing (18 tests, all passing)

**In Progress:**
- 🔄 Equivalence class testing
- 🔄 Decision table testing

**Pending:**
- ⏳ Coverage report generation
- ⏳ Test report documentation
- ⏳ Eclipse migration

## REQUIREMENTS

- [x] Develop using VSCode (will be converted to Eclipse before submission)
- [ ] Export your Java Project as the given format with your student ID: SEDS514_Project1_StdID1_StdID2.zip
- [x] Write object-oriented version of NextDate program in Java
- [ ] Develop and code unit tests using:
  - [x] Boundary value testing (18 tests - all passing)
  - [ ] Equivalence class testing
  - [ ] Decision table techniques
- [x] Run tests using JUnit 4
- [ ] Document results as a test report comparing test design techniques
- [ ] Achieve 100% statement coverage and 85% branch coverage

## PROJECT PLAN

### 1. Update Project Documentation
- [x] Modify README to reflect VSCode usage
- [ ] Document VSCode setup instructions
- [ ] Create Eclipse migration guide

### 2. VSCode Setup & Extensions
- [x] Install VSCode Java Extension Pack
  - Includes Java language support, debugging, and JUnit runner
- [x] Install Coverage Gutters extension
  - Visualizes code coverage in the editor
- [x] Configure Java project structure
  - Ensure compatibility with both VSCode and Eclipse

### 3. Build & Test Configuration
- [x] Set up Maven build system
  - Ensures Eclipse compatibility for later migration
- [x] Configure JUnit 4 dependencies
- [x] Set up JaCoCo for code coverage
  - Industry standard, works with both VSCode and Eclipse
  - EclEmma uses JaCoCo underneath

### 4. Development Workflow
- [x] Create NextDate program with proper package structure
  - Implemented with year range 1812-2100
  - Leap year detection
  - Month-specific day validation
- [x] Develop test suite for boundary value testing
  - 18 tests covering year, month, and day boundaries
  - All tests passing
- [ ] Develop test suite for equivalence class testing
- [ ] Develop test suite for decision table testing
- [ ] Run tests and verify coverage using JaCoCo
- [ ] Generate coverage reports
- [ ] Verify 100% statement and 85% branch coverage

### 5. Test Report
- [ ] Document test cases for each technique
- [ ] Compare effectiveness of test design techniques
- [ ] Include coverage analysis
- [ ] Document findings and observations

### 6. Eclipse Migration Path
- [ ] Use standard Java project structure throughout
- [ ] Keep all dependencies in Maven/Gradle
- [ ] Import project into Eclipse (Maven/Gradle import)
- [ ] Verify tests run in Eclipse
- [ ] Verify coverage with EclEmma
- [ ] Export as required zip format

## TEAM

- Baris Yenigun - [@barisyenigun07](https://github.com/barisyenigun07)
- Mustafacan Koc - [@cankoc35](https://github.com/cankoc35)
- Umut Akin - [@umutakin-dev](https://github.com/umutakin-dev)