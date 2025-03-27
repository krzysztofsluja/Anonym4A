# Anonymization Mechanism: Summary & Discussion Recap

## 1. Parsing Strategy
- **Cascade Parsing:**  
  - The mechanism will attempt to parse user input as a **CompilationUnit** first.
  - If that fails, it falls back to **ClassOrInterfaceDeclaration**.
  - If that still fails, it then tries **MethodDeclaration**.
  - Finally, if necessary, it attempts to parse individual **Statements** or **Expressions**.
- **Stopping on Success:**  
  - As soon as a parsing attempt is successful, the cascade stops and the parsed content is returned.
- **Logging:**  
  - Only simple, internal logging is performed. Each failed attempt logs the specific level that wasn’t suitable.
- **Error Handling:**  
  - In case all parsing levels fail, internal logs are generated and a generic error message is provided to the user regarding an incorrect input value.

## 2. Anonymization Strategy
- **Per-Input Process:**  
  - Each user input is treated as a separate process and starts fresh.
- **Naming Conventions:**  
  - Anonymized identifiers (e.g., `Class1`, `Method1`, `var1`) are provided via a properties file.
- **Anonymization Context:**  
  - A centralized, in-memory map is used to maintain mappings between original identifiers and their anonymized values.

## 3. Initial Code Cleanup and Element Removal
- **Elements to Remove:**  
  - **Package Declarations** (`package ...;`)
  - **Import Statements** (`import ...;`)
  - **Comments** (single-line `//...`, multi-line `/*...*/`, Javadoc `/**...*/`)
  - **Generic Logging Statements** (e.g., `logger.*(...)`, `System.out.*(...)`)
- **Method:**  
  - These removals will be handled using JavaParser’s AST-based methods for precision.

## 4. Anonymization at Compilation Unit Level
- **Top-Level Types:**  
  - Anonymization applies to top-level type declarations (classes, interfaces, enums, annotations).

## 5. Anonymization at Class/Interface Level
- **Type Renaming:**  
  - Class, interface, and enum names are anonymized.
  - Superclass and implemented interface names are anonymized but their relationships are preserved.
- **Nested Types:**  
  - Nested types use a separate numbering system from top-level types to maintain clarity.

## 6. Anonymization at Method/Constructor Level
- **Methods:**  
  - Methods are anonymized using a sequential naming pattern (e.g., `Method1`, `Method2`, etc.).
- **Constructors:**  
  - Constructors are renamed as `Constructor1`, `Constructor2`, etc., aligning with the anonymized class name.
- **Parameters:**  
  - Parameter names (and types, if required) are anonymized according to the values in the properties file.

## 7. Anonymization of Local Variables and Statements
- **Global Numbering:**  
  - Local variable names and exception handler parameters use a global numbering scheme to avoid ambiguity.

## 8. Consistency and Integrity Check
- **Automated Re-parsing:**  
  - After anonymization, the output is re-parsed using JavaParser to ensure syntactic correctness. This acts as a sanity check.
- **Note:**  
  - While we perform this re-parsing internally, no further checks (such as validating the mapping list) are carried out before delivering the result to the user.

## 9. Robust Error Handling
- **Exception Handling:**  
  - Each stage/method in the process is designed to throw specific exceptions.
  - A structured error handling mechanism is in place to catch these exceptions, log them internally, and control the flow accordingly.
  
## 10. Testing and Validation
- **Test Suite:**  
  - Unit tests are planned for each individual stage (parsing, element removal, renaming, etc.).
  - Integration tests and edge case tests (e.g., incomplete snippets, deeply nested structures) will ensure the robustness of the mechanism.
  
## 11. Documentation and Maintenance
- **README:**  
  - Detailed documentation will be maintained in a Markdown README.
  - Discussion on the structure of the documentation is postponed until after the development stage.
