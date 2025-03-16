# Anonym4AI – Project Documentation

---

## 1. General Project Information

- **Official Project Name:** `Anonym4AI`
- **Intended Users:** Internal development and QA team
- **Final Decision Makers:** Collaborative approval by team members

---

## 2. Business Objectives and Goals

### Primary Goal

Anonymize sensitive information in Java and JavaScript code snippets—including variable names, method names, class names, strings, and API endpoints—while automatically removing comments and logging statements. This ensures snippets are safe for interaction with external AI models.

### Specific Problem to Solve

Protect sensitive or proprietary data when sharing code externally, preventing potential information leakage.

### Expected Outcomes and Benefits

- Creation of securely anonymized code snippets.
- Automatic generation of clear mappings between original identifiers and anonymized versions.
  - Example: Original variable `start` (datatype: integer) → `var1`
- Increased efficiency and safety when interacting with AI services.

---

## 3. Functional Requirements

### Input Method

- Users will **paste code snippets directly** into the application.
- Future support for indirect pasting via shortcut may be considered.

### Code Analysis and Anonymization

**By default, the application anonymizes:**
- Variable names
- Method/function names
- Class names
- String literals
- URLs and API endpoints
- Credentials or tokens (if detected)

**Application will automatically remove:**
- Comments
- Logging statements

**Customizable User Settings:**
- Users can selectively enable or disable anonymization options via settings.
- Users can define additional elements to anonymize, tailoring the process to specific needs.

**Editable Naming Conventions:**
- Default anonymized names will follow a consistent pattern (e.g., `var1`, `method1`).
- Users can customize these patterns or manually edit anonymized names.

**Mappings:**
- Application generates mappings between original identifiers and anonymized identifiers.
- Clearly formatted for easy reference.

---

## 4. Output and User Interface Requirements

- Provides a real-time **side-by-side comparison** between original and anonymized snippets.
- Users can **copy** anonymized snippets directly to the clipboard.
- Mappings between original and anonymized identifiers can be copied or exported.
- Snippets maintain the exact syntax and formatting of the original code.

---

## 5. Technical Requirements and Decisions

- **Desktop Framework:** Java Swing
- **Code Parsing Library:** JavaParser
  - JavaParser provides an Abstract Syntax Tree (AST) for Java code, facilitating easy analysis and transformation.
- **Cross-platform Compatibility:** Windows, macOS, Linux
- **Database/Storage:** No centralized database; all data is handled locally per user.
- **Technical Standards:** Adherence to standard Java and Java Swing best practices; no external licensing-restricted libraries unless explicitly approved.

---

## 6. Performance Requirements

- Application optimized for **maximum speed**, ensuring minimal latency and quick responsiveness.
- **No predefined maximum size or complexity limitation** for input snippets, emphasizing the need for efficiency.

---

