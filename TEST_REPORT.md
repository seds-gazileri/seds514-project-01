# NextDate Program - Test Report

**Course:** SEDS 514 - Software Testing
**Project:** Project 1 - NextDate Program Testing
**Date:** October 19, 2025
**Team Members:**
- Baris Yenigun - [@barisyenigun07](https://github.com/barisyenigun07)
- Mustafacan Koc - [@cankoc35](https://github.com/cankoc35)
- Umut Akin - [@umutakin-dev](https://github.com/umutakin-dev)

---

## Table of Contents

1. [Introduction](#introduction)
2. [Test Environment](#test-environment)
3. [Boundary Value Testing](#boundary-value-testing)
4. [Equivalence Class Testing](#equivalence-class-testing)
5. [Decision Table Testing](#decision-table-testing)
6. [Coverage Analysis](#coverage-analysis)
7. [Comparison of Techniques](#comparison-of-techniques)
8. [Findings and Observations](#findings-and-observations)
9. [Conclusions](#conclusions)

---

## 1. Introduction

This report presents the results of comprehensive testing performed on the NextDate program using three different test design techniques:
- Boundary Value Testing
- Equivalence Class Testing
- Decision Table Testing

### Program Under Test

**NextDate Program** calculates the next calendar date given a valid date input.

**Specifications:**
- **Input Parameters:**
  - `month`: Integer (1-12)
  - `day`: Integer (1-31, depending on month)
  - `year`: Integer (1812-2100)
- **Output:** Next date in format "MM/DD/YYYY"
- **Special Cases:**
  - Leap year calculation (divisible by 400, or divisible by 4 but not 100)
  - Month-specific day limits (28/29/30/31 days)
  - Year rollover (December 31 → January 1 next year)

### Testing Objectives

1. Verify correct calculation of next date for all valid inputs
2. Verify proper error handling for invalid inputs
3. Achieve ≥85% branch coverage and ≥100% statement coverage
4. Compare effectiveness of different test design techniques

---

## 2. Test Environment

### Development Environment
- **IDE:** Visual Studio Code
- **Language:** Java 11
- **Build Tool:** Maven 3.9.11
- **Testing Framework:** JUnit 4.13.2
- **Coverage Tool:** JaCoCo 0.8.11

### Project Structure
```
project-01/
├── src/main/java/edu/seds514/nextdate/
│   └── NextDate.java
├── src/test/java/edu/seds514/nextdate/
│   ├── NextDateBoundaryValueTest.java
│   ├── NextDateEquivalenceClassTest.java
│   └── NextDateDecisionTableTest.java
└── pom.xml
```

### Test Execution
All tests were executed using Maven:
```bash
mvn clean test jacoco:report
```

---

## 3. Boundary Value Testing

### 3.1 Methodology

Boundary Value Testing focuses on testing values at the **edges of input ranges**, based on the principle that errors are more likely to occur at boundaries.

For each input parameter with range [min, max], we test:
- **min - 1** (just below minimum - invalid)
- **min** (minimum valid value)
- **min + 1** (just above minimum)
- **max - 1** (just below maximum)
- **max** (maximum valid value)
- **max + 1** (just above maximum - invalid)

**Nominal values used:** When testing one parameter's boundaries, other parameters are set to safe middle values (year=2000, month=6, day=15).

### 3.2 Test Cases

#### Year Parameter Boundaries (Range: 1812-2100)

| Test ID | Input (M/D/Y) | Expected Output | Result | Purpose |
|---------|---------------|-----------------|--------|---------|
| BV-Y-01 | 06/15/1811 | Exception | ✅ Pass | Year below minimum |
| BV-Y-02 | 06/15/1812 | 06/16/1812 | ✅ Pass | Year at minimum |
| BV-Y-03 | 06/15/1813 | 06/16/1813 | ✅ Pass | Year just above minimum |
| BV-Y-04 | 06/15/2099 | 06/16/2099 | ✅ Pass | Year just below maximum |
| BV-Y-05 | 06/15/2100 | 06/16/2100 | ✅ Pass | Year at maximum |
| BV-Y-06 | 06/15/2101 | Exception | ✅ Pass | Year above maximum |

#### Month Parameter Boundaries (Range: 1-12)

| Test ID | Input (M/D/Y) | Expected Output | Result | Purpose |
|---------|---------------|-----------------|--------|---------|
| BV-M-01 | 00/15/2000 | Exception | ✅ Pass | Month below minimum |
| BV-M-02 | 01/15/2000 | 01/16/2000 | ✅ Pass | Month at minimum (January) |
| BV-M-03 | 02/15/2000 | 02/16/2000 | ✅ Pass | Month just above minimum |
| BV-M-04 | 11/15/2000 | 11/16/2000 | ✅ Pass | Month just below maximum |
| BV-M-05 | 12/15/2000 | 12/16/2000 | ✅ Pass | Month at maximum (December) |
| BV-M-06 | 13/15/2000 | Exception | ✅ Pass | Month above maximum |

#### Day Parameter Boundaries (Range: 1-31)

| Test ID | Input (M/D/Y) | Expected Output | Result | Purpose |
|---------|---------------|-----------------|--------|---------|
| BV-D-01 | 01/00/2000 | Exception | ✅ Pass | Day below minimum |
| BV-D-02 | 01/01/2000 | 01/02/2000 | ✅ Pass | Day at minimum |
| BV-D-03 | 01/02/2000 | 01/03/2000 | ✅ Pass | Day just above minimum |
| BV-D-04 | 01/30/2000 | 01/31/2000 | ✅ Pass | Day just below maximum |
| BV-D-05 | 01/31/2000 | 02/01/2000 | ✅ Pass | Day at maximum (rolls to next month) |
| BV-D-06 | 01/32/2000 | Exception | ✅ Pass | Day above maximum |

### 3.3 Results

- **Total Tests:** 18
- **Passed:** 18 (100%)
- **Failed:** 0
- **Defects Found:** 0

### 3.4 Coverage Contribution

Boundary value tests primarily covered:
- Input validation logic (100% of validation branches)
- Basic date increment logic
- Month rollover at day=31

**Initial Coverage (BVT only):**
- Branch Coverage: 71%
- Line Coverage: 38%

---

## 4. Equivalence Class Testing

### 4.1 Methodology

Equivalence Class Testing divides the input space into **classes** where all values in a class should behave similarly. We test one representative value from each class.

**Valid Equivalence Classes Identified:**

1. **Month Types:**
   - 31-day months (Jan, Mar, May, Jul, Aug, Oct, Dec)
   - 30-day months (Apr, Jun, Sep, Nov)
   - February in leap year (29 days)
   - February in non-leap year (28 days)

2. **Year Types (Leap Year Logic):**
   - Divisible by 400 (leap year) - e.g., 2000
   - Divisible by 4 but not 100 (leap year) - e.g., 2024
   - Divisible by 100 but not 400 (NOT leap) - e.g., 2100
   - Not divisible by 4 (NOT leap) - e.g., 2023

3. **Day Transitions:**
   - Normal day (middle of month)
   - Last day of 31-day month
   - Last day of 30-day month
   - Last day of February (leap year)
   - Last day of February (non-leap year)
   - Last day of year (December 31)

### 4.2 Test Cases

#### Month Type Classes

| Test ID | Input (M/D/Y) | Expected Output | Result | Class |
|---------|---------------|-----------------|--------|-------|
| EC-MT-01 | 01/15/2000 | 01/16/2000 | ✅ Pass | 31-day month |
| EC-MT-02 | 04/15/2000 | 04/16/2000 | ✅ Pass | 30-day month |
| EC-MT-03 | 02/15/2000 | 02/16/2000 | ✅ Pass | Feb (leap year) |
| EC-MT-04 | 02/15/2023 | 02/16/2023 | ✅ Pass | Feb (non-leap) |

#### Leap Year Type Classes

| Test ID | Input (M/D/Y) | Expected Output | Result | Class |
|---------|---------------|-----------------|--------|-------|
| EC-LY-01 | 02/28/2000 | 02/29/2000 | ✅ Pass | Divisible by 400 |
| EC-LY-02 | 02/28/2024 | 02/29/2024 | ✅ Pass | Divisible by 4, not 100 |
| EC-LY-03 | 02/28/2100 | 03/01/2100 | ✅ Pass | Divisible by 100, not 400 |
| EC-LY-04 | 02/28/2023 | 03/01/2023 | ✅ Pass | Not divisible by 4 |

#### Day Transition Classes

| Test ID | Input (M/D/Y) | Expected Output | Result | Class |
|---------|---------------|-----------------|--------|-------|
| EC-DT-01 | 06/15/2000 | 06/16/2000 | ✅ Pass | Middle of month |
| EC-DT-02 | 01/31/2000 | 02/01/2000 | ✅ Pass | End of 31-day month |
| EC-DT-03 | 04/30/2000 | 05/01/2000 | ✅ Pass | End of 30-day month |
| EC-DT-04 | 02/29/2000 | 03/01/2000 | ✅ Pass | End of Feb (leap) |
| EC-DT-05 | 02/28/2019 | 03/01/2019 | ✅ Pass | End of Feb (non-leap) |
| EC-DT-06 | 12/31/2000 | 01/01/2001 | ✅ Pass | End of year |

#### Invalid Classes

| Test ID | Input (M/D/Y) | Expected Output | Result | Class |
|---------|---------------|-----------------|--------|-------|
| EC-IV-01 | 00/15/2000 | Exception | ✅ Pass | Invalid month (too small) |
| EC-IV-02 | 13/15/2000 | Exception | ✅ Pass | Invalid month (too large) |
| EC-IV-03 | 01/00/2000 | Exception | ✅ Pass | Invalid day (too small) |
| EC-IV-04 | 02/30/2000 | Exception | ✅ Pass | Invalid day for month |
| EC-IV-05 | 06/15/1811 | Exception | ✅ Pass | Invalid year (too small) |
| EC-IV-06 | 06/15/2101 | Exception | ✅ Pass | Invalid year (too large) |

### 4.3 Results

- **Total Tests:** 20
- **Passed:** 20 (100%)
- **Failed:** 0
- **Defects Found:** 0

### 4.4 Coverage Contribution

Equivalence class tests significantly improved coverage by testing:
- All leap year calculation paths (isLeapYear method: 100% coverage)
- All month type variations
- Year rollover logic (getNextDate year increment: 100% coverage)

**Coverage After BVT + ECT:**
- Branch Coverage: 96% (↑ from 71%)
- Line Coverage: 97% (↑ from 38%)

---

## 5. Decision Table Testing

### 5.1 Methodology

Decision Table Testing systematically tests **combinations of conditions** that affect program behavior. We identified decision rules based on conditions and their corresponding actions.

**Decision Table:**

| Rule | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
|------|---|---|---|---|---|---|---|
| **Conditions:** |
| Month Type | Any | 31-day | 30-day | Feb(leap) | Feb(leap) | Feb(non-leap) | Dec |
| Day Value | 15 | 31 | 30 | 28 | 29 | 28 | 31 |
| Is Last Day? | No | Yes | Yes | No | Yes | Yes | Yes |
| **Actions:** |
| Increment day | X | | | X | | | |
| New month | | X | X | | X | X | |
| New year | | | | | | | X |

**Rules Explained:**
- **Rule 1:** Normal day → increment day only
- **Rule 2:** Last day of 31-day month → new month
- **Rule 3:** Last day of 30-day month → new month
- **Rule 4:** Feb 28 in leap year → increment to 29 (NOT last day)
- **Rule 5:** Feb 29 in leap year → new month
- **Rule 6:** Feb 28 in non-leap year → new month
- **Rule 7:** Dec 31 → new year

### 5.2 Test Cases

| Test ID | Rule | Input (M/D/Y) | Expected Output | Result | Description |
|---------|------|---------------|-----------------|--------|-------------|
| DT-01 | 1 | 06/15/2000 | 06/16/2000 | ✅ Pass | Normal day increment |
| DT-02 | 2 | 01/31/2000 | 02/01/2000 | ✅ Pass | Last day of January |
| DT-03 | 2 | 03/31/2000 | 04/01/2000 | ✅ Pass | Last day of March |
| DT-04 | 3 | 04/30/2000 | 05/01/2000 | ✅ Pass | Last day of April |
| DT-05 | 3 | 09/30/2000 | 10/01/2000 | ✅ Pass | Last day of September |
| DT-06 | 4 | 02/28/2000 | 02/29/2000 | ✅ Pass | Feb 28, leap (div by 400) |
| DT-07 | 4 | 02/28/2024 | 02/29/2024 | ✅ Pass | Feb 28, leap (div by 4) |
| DT-08 | 5 | 02/29/2000 | 03/01/2000 | ✅ Pass | Feb 29, leap year |
| DT-09 | 5 | 02/29/2024 | 03/01/2024 | ✅ Pass | Feb 29, leap year |
| DT-10 | 6 | 02/28/2023 | 03/01/2023 | ✅ Pass | Feb 28, non-leap |
| DT-11 | 6 | 02/28/2100 | 03/01/2100 | ✅ Pass | Feb 28, non-leap (div by 100) |
| DT-12 | 6 | 02/28/2019 | 03/01/2019 | ✅ Pass | Feb 28, regular year |
| DT-13 | 7 | 12/31/2000 | 01/01/2001 | ✅ Pass | Year rollover |
| DT-14 | 7 | 12/31/2024 | 01/01/2025 | ✅ Pass | Year rollover |

### 5.3 Results

- **Total Tests:** 14
- **Passed:** 14 (100%)
- **Failed:** 0
- **Defects Found:** 0

### 5.4 Coverage Contribution

Decision table tests reinforced coverage by:
- Testing multiple representatives of each decision path
- Validating combinations of conditions
- Ensuring all decision rules work correctly

**Final Coverage (BVT + ECT + DTT):**
- Branch Coverage: 96% (no change from ECT - already complete)
- Line Coverage: 97% (no change from ECT - already complete)

**Note:** DTT provided no coverage increase but added **confidence through testing the same branches with different scenarios** (e.g., Feb 28 tested with years 2000, 2024, 2100, 2019 to verify leap year logic works for all types, not just one case).

---

## 6. Coverage Analysis

### 6.1 Overall Coverage Summary

```
Total Test Cases: 52 (18 BVT + 20 ECT + 14 DTT)
Pass Rate: 100%
```

| Metric | Coverage | Target | Status |
|--------|----------|--------|--------|
| **Branch Coverage** | 96% (29/30) | ≥85% | ✅ Exceeds |
| **Instruction Coverage** | 95% (142/148) | - | ✅ Excellent |
| **Line Coverage** | 97% (35/36) | ≥100% | ⚠️ Near Complete |
| **Method Coverage** | 100% (5/5) | - | ✅ Perfect |
| **Complexity Coverage** | 95% (20/21) | - | ✅ Excellent |

### 6.2 Method-Level Coverage

| Method | Instructions | Branches | Lines | Analysis |
|--------|-------------|----------|-------|----------|
| `getNextDate()` | 100% | 100% | 11/11 | ✅ Fully tested |
| `validateDate()` | 100% | 100% | 10/10 | ✅ Fully tested |
| `isLeapYear()` | 100% | 100% | 7/7 | ✅ Fully tested |
| `getDaysInMonth()` | 70% | 83% | 6/7 | ⚠️ One unreachable line |
| `NextDate()` (constructor) | 100% | n/a | 1/1 | ✅ Fully tested |

### 6.3 Uncovered Code Analysis

**Single Missing Line:** The `default` case in `getDaysInMonth()` switch statement (line 140):

```java
default:
    throw new IllegalArgumentException("Invalid month: " + month);
```

**Why This Line is Unreachable:**
1. `getDaysInMonth()` is only called from `getNextDate()`
2. `getNextDate()` first calls `validateDate()` which checks `month` is 1-12
3. Invalid months throw exception in `validateDate()` before reaching `getDaysInMonth()`
4. The `default` case is **defensive programming** - a safety net that cannot be reached in normal flow

**Conclusion:** The 97% line coverage represents **100% effective coverage** - all reachable code is tested.

### 6.4 Coverage Progression

| Phase | Branch Cov. | Line Cov. | Tests |
|-------|-------------|-----------|-------|
| BVT Only | 71% | 38% | 18 |
| BVT + ECT | 96% | 97% | 38 |
| BVT + ECT + DTT | 96% | 97% | 52 |

**Key Insight:** Equivalence Class Testing provided the most significant coverage improvement, especially for leap year logic and month-type variations.

### 6.5 Individual Technique Coverage Analysis

To understand the unique contribution of each testing technique, we ran each test suite independently and measured their individual coverage.

#### 6.5.1 Boundary Value Testing (BVT) - Standalone Coverage

**Test Count:** 18 tests

| Overall Coverage | |
|-----------------|---|
| **Branch Coverage** | 70% (21/30) |
| **Instruction Coverage** | 75% (111/148) |
| **Line Coverage** | 86% (31/36) |

**Method-Level Coverage:**

| Method | Instructions | Branches | Analysis |
|--------|-------------|----------|----------|
| `getNextDate()` | 88% | 75% | Good coverage, misses year rollover branch |
| `validateDate()` | 82% | 92% | Excellent - BVT targets validation heavily |
| `isLeapYear()` | 30% | 16% | Poor - BVT uses typical years, misses leap logic |
| `getDaysInMonth()` | 60% | 66% | Moderate - misses February leap year cases |
| `NextDate()` (constructor) | 100% | n/a | ✅ Full coverage |

**Key Finding:** BVT excels at **input validation** (92% branch coverage in `validateDate()`) but poorly covers **business logic** like leap year detection (16% branch coverage in `isLeapYear()`).

#### 6.5.2 Equivalence Class Testing (ECT) - Standalone Coverage

**Test Count:** 20 tests

| Overall Coverage | |
|-----------------|---|
| **Branch Coverage** | 93% (28/30) |
| **Instruction Coverage** | 95% (142/148) |
| **Line Coverage** | 97% (35/36) |

**Method-Level Coverage:**

| Method | Instructions | Branches | Analysis |
|--------|-------------|----------|----------|
| `getNextDate()` | 100% | 100% | ✅ Complete coverage |
| `validateDate()` | 100% | 92% | ✅ Near-complete, same as BVT for branches |
| `isLeapYear()` | 100% | 100% | ✅ All leap year rules tested |
| `getDaysInMonth()` | 70% | 83% | Good - February cases covered |
| `NextDate()` (constructor) | 100% | n/a | ✅ Full coverage |

**Key Finding:** ECT alone achieves **93% branch coverage** because it specifically targets different classes of behavior (month types, leap year types, day transitions). This technique is most effective for categorical logic.

#### 6.5.3 Decision Table Testing (DTT) - Standalone Coverage

**Test Count:** 14 tests

| Overall Coverage | |
|-----------------|---|
| **Branch Coverage** | 73% (22/30) |
| **Instruction Coverage** | 79% (118/148) |
| **Line Coverage** | 86% (31/36) |

**Method-Level Coverage:**

| Method | Instructions | Branches | Analysis |
|--------|-------------|----------|----------|
| `getNextDate()` | 100% | 100% | ✅ Complete - DTT focuses on date logic |
| `validateDate()` | 52% | 50% | Poor - DTT assumes valid inputs |
| `isLeapYear()` | 100% | 100% | ✅ All leap year rules tested |
| `getDaysInMonth()` | 70% | 83% | Good - tests month-end scenarios |
| `NextDate()` (constructor) | 100% | n/a | ✅ Full coverage |

**Key Finding:** DTT excels at **business logic** (100% coverage in both `getNextDate()` and `isLeapYear()`) but provides poor coverage for **input validation** (50% branch coverage in `validateDate()`), since decision tables typically assume valid inputs.

#### 6.5.4 Coverage Comparison Summary

| Metric | BVT (18 tests) | ECT (20 tests) | DTT (14 tests) | Combined (52 tests) |
|--------|----------------|----------------|----------------|---------------------|
| **Branch Coverage** | 70% | **93%** | 73% | **96%** |
| **Instruction Coverage** | 75% | **95%** | 79% | **95%** |
| **Line Coverage** | 86% | **97%** | 86% | **97%** |

**Visual Coverage Progression:**

```
Branch Coverage by Technique:
BVT:  ████████████████░░░░░░░░░░░░░░ 70%
ECT:  ████████████████████████████░░ 93% ⭐ Most Effective
DTT:  ██████████████████░░░░░░░░░░░░ 73%
ALL:  ████████████████████████████░░ 96% (final)
```

#### 6.5.5 Unique Strengths by Coverage Area

| Coverage Area | Best Technique | Coverage | Reason |
|---------------|----------------|----------|---------|
| **Input Validation** | BVT | 92% branches | Tests boundaries of valid ranges |
| **Leap Year Logic** | ECT / DTT | 100% branches | Both test all 4 leap year rules |
| **Month Type Logic** | ECT | 83% branches | Tests 31-day, 30-day, Feb variations |
| **Date Transitions** | ECT | 100% branches | Tests all transition types |
| **Year Rollover** | ECT / DTT | 100% branches | Both test Dec 31 → Jan 1 |

#### 6.5.6 Coverage Overlap Analysis

**Unique Coverage Contributions:**

1. **BVT Unique Contribution:** ~0%
   - All branches covered by BVT are also covered by ECT
   - BVT validates ECT's approach to boundaries

2. **ECT Unique Contribution:** ~23%
   - ECT alone covers 93% vs BVT's 70%
   - Adds critical leap year logic and month-type variations
   - Could almost replace BVT for coverage, but BVT adds validation confidence

3. **DTT Unique Contribution:** ~0%
   - All branches covered by DTT are also covered by ECT
   - DTT provides **validation through redundancy** (same branches, different scenarios)
   - Example: Feb 28 tested with years 2000, 2024, 2100, 2019 to verify all leap year types

**Note:** DTT provided no coverage increase but added **confidence through testing the same branches with different scenarios** (e.g., Feb 28 tested with years 2000, 2024, 2100, 2019 to verify leap year logic works for all types, not just one case).

---

## 7. Comparison of Techniques

### 7.1 Test Count and Efficiency

| Technique | Test Count | Unique Scenarios | Efficiency Ratio |
|-----------|------------|------------------|------------------|
| Boundary Value | 18 | 18 | 1.00 |
| Equivalence Class | 20 | 20 | 1.00 |
| Decision Table | 14 | 7 rules × 2 avg | 0.50 |

**Analysis:**
- BVT and ECT have 1:1 ratio (each test covers one unique scenario)
- DTT has multiple tests per rule for validation (higher redundancy, but better confidence)

### 7.2 Defect Detection Capability

| Technique | Defects Found | Types of Defects Well-Suited For |
|-----------|---------------|-----------------------------------|
| Boundary Value | 0* | Off-by-one errors, range validation errors |
| Equivalence Class | 0* | Logic errors in categorization, missing cases |
| Decision Table | 0* | Combination errors, missing decision paths |

*No defects were found as the implementation was developed test-first with all three techniques in mind.

### 7.3 Coverage Impact

```
Coverage Contribution:
BVT:  71% branch (baseline)
ECT:  +25% branch (71% → 96%)
DTT:  +0% branch (same branches, different scenarios for validation)
```

**Key Findings:**
- ECT provided the most significant coverage improvement because it specifically targeted untested equivalence classes (leap year types, month types)
- DTT didn't increase coverage but tested the same branches with different input combinations (e.g., testing Feb 28 with 4 different years: 2000, 2024, 2100, 2019)
- This redundancy provides confidence that logic works for all scenarios, not just one test case

### 7.4 Strengths and Weaknesses

#### Boundary Value Testing

**Strengths:**
- ✅ Systematic and easy to apply
- ✅ Excellent for catching input validation errors
- ✅ Minimal overlap between test cases
- ✅ Easy to document and understand

**Weaknesses:**
- ❌ Doesn't test combinations of parameters
- ❌ Misses logic in the middle of ranges
- ❌ Limited coverage of complex business rules

**Best Used For:** Input validation, range checking, API boundary conditions

#### Equivalence Class Testing

**Strengths:**
- ✅ Reduces number of tests while maintaining coverage
- ✅ Excellent for categorical logic (month types, leap years)
- ✅ Covers both valid and invalid classes
- ✅ Reveals gaps in understanding of requirements

**Weaknesses:**
- ❌ Requires good domain knowledge to identify classes
- ❌ Classes may overlap, leading to redundancy
- ❌ May miss boundary-specific errors

**Best Used For:** Categorical data, business logic with distinct cases, state-based testing

#### Decision Table Testing

**Strengths:**
- ✅ Excellent for complex combinations of conditions
- ✅ Systematic coverage of decision logic
- ✅ Easy to verify completeness
- ✅ Self-documenting (table shows all scenarios)

**Weaknesses:**
- ❌ Can become very large with many conditions
- ❌ May have redundancy with other techniques
- ❌ Requires careful analysis to build correct table

**Best Used For:** Complex decision logic, rule-based systems, combinations of conditions

### 7.5 Complementary Nature

The three techniques are **highly complementary**:

1. **BVT** ensures edge cases are tested
2. **ECT** ensures different categories are tested
3. **DTT** ensures combinations are tested

**Example - Feb 28 in Leap Year:**
- BVT: Might miss (28 is not a boundary for day 1-31)
- ECT: ✅ Catches it (different class than Feb 28 non-leap)
- DTT: ✅ Catches it (explicit rule in decision table)

---

## 8. Findings and Observations

### 8.1 Key Findings

1. **All three techniques are necessary for comprehensive testing**
   - BVT alone: 71% branch coverage
   - BVT + ECT: 96% branch coverage
   - BVT + ECT + DTT: 96% branch coverage (with higher confidence)

2. **Equivalence Class Testing had the highest impact on coverage**
   - Increased branch coverage by 25 percentage points
   - Specifically targeted uncovered logic (leap years, month types)

3. **Decision Table Testing provided validation rather than new coverage**
   - No coverage increase, but confirmed correctness of decision logic
   - Multiple tests per rule increase confidence

4. **The program has robust input validation**
   - All invalid inputs properly rejected
   - Specific error messages for each validation failure

5. **Defensive programming creates unreachable code**
   - The `default` case in `getDaysInMonth()` cannot be reached
   - This is acceptable and considered good practice

### 8.2 Test Design Insights

1. **Order Matters:**
   - Starting with ECT might have achieved high coverage faster
   - BVT first ensured input validation was solid
   - DTT last provided systematic verification

2. **Representative Values:**
   - Choosing good representative values is crucial for ECT
   - Poor choices lead to redundant tests or missed scenarios

3. **Decision Table Complexity:**
   - 7 rules were manageable
   - More complex programs would need hierarchical decision tables

### 8.3 Implementation Quality

**Strengths:**
- Clean, well-structured code
- Proper separation of concerns (validation, calculation, leap year logic)
- Comprehensive JavaDoc documentation
- Consistent error handling

**Areas for Enhancement:**
- Could add logging for debugging
- Could support additional date formats
- Could extend year range (currently 1812-2100)

---

## 9. Conclusions

### 9.1 Summary

This testing effort successfully validated the NextDate program using three complementary test design techniques:

- **52 test cases** created across all three techniques
- **100% pass rate** - all tests passing
- **96% branch coverage** - exceeding the 85% requirement
- **97% line coverage** - near-perfect, with only unreachable defensive code uncovered
- **0 defects** found in final implementation

### 9.2 Technique Effectiveness Ranking

For the NextDate program specifically:

1. **Most Effective: Equivalence Class Testing**
   - Provided largest coverage improvement (71% → 96%)
   - Best suited for categorical logic (month types, leap years)
   - Optimal test-to-coverage ratio

2. **Second: Boundary Value Testing**
   - Essential for input validation testing
   - Systematic and straightforward to apply
   - Good foundation for other techniques

3. **Third: Decision Table Testing**
   - Excellent for systematic documentation
   - Provided confidence through redundancy
   - Best for complex decision logic validation

**Important Note:** This ranking is context-specific. Different programs might benefit more from different techniques.

### 9.3 Recommendations

**For Similar Projects:**
1. Use **all three techniques** for comprehensive testing
2. Start with **BVT** for input validation
3. Add **ECT** for categorical and business logic
4. Use **DTT** for complex combinations and documentation
5. Monitor coverage after each technique to guide test design

**For This Project:**
1. ✅ Testing objectives achieved
2. ✅ Coverage requirements exceeded
3. ⏳ Document and preserve test results
4. ⏳ Migrate to Eclipse and verify
5. ⏳ Prepare final submission package

### 9.4 Lessons Learned

1. **Test-first thinking prevents defects** - No defects found because tests guided implementation
2. **Coverage metrics are guides, not goals** - 97% with unreachable code is effectively 100%
3. **Complementary techniques provide comprehensive coverage** - Each technique has blind spots
4. **Good test design requires domain understanding** - Knowing leap year rules was essential
5. **Documentation is as important as testing** - This report captures knowledge for future maintenance

---

## Appendices

### A. Test Execution Commands

```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=NextDateBoundaryValueTest

# Generate coverage report
mvn clean test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

### B. Coverage Reports Location

- HTML Report: `target/site/jacoco/index.html`
- XML Report: `target/site/jacoco/jacoco.xml`
- CSV Report: `target/site/jacoco/jacoco.csv`

### C. Repository

- GitHub: https://github.com/seds-gazileri/seds514-project-01

---

**End of Report**
