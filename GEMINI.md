# Development Guidelines and Rules

## Programming Languages & Preferences

### Supported Languages
- **Java**: Spring Boot, Maven/Gradle, JUnit
- **Python**: FastAPI/Django, pytest, type hints
- **JavaScript/TypeScript**: Node.js, React, Jest
- **Go**: Gin/Echo, standard library testing

### Design Patterns by Category

#### Creational Patterns (Object Creation)
1. **Builder Pattern**: For complex object construction with optional parameters
2. **Factory Pattern**: For object creation abstraction and dependency injection
3. **Singleton Pattern**: For single instance management (use sparingly)

#### Behavioral Patterns (Object Interaction)
1. **Strategy Pattern**: For algorithm selection and interchangeable behaviors
2. **Observer Pattern**: For event-driven programming and reactive systems
3. **Template Method Pattern**: For defining algorithm structure with customizable steps
4. **Command Pattern**: For encapsulating requests and supporting undo/redo

#### Structural Patterns (Object Composition)
1. **Adapter Pattern**: For interface compatibility and legacy system integration
2. **Decorator Pattern**: For extending functionality without inheritance
3. **Facade Pattern**: For simplifying complex subsystem interfaces

#### Language-Specific Patterns
- **Generator Pattern** (Python, JavaScript): For data streaming and lazy evaluation
- **Repository Pattern** (Java, C#): For data access abstraction
- **Middleware Pattern** (Node.js, Go): For request/response processing chains
- **Dependency Injection** (Java Spring, Python FastAPI): For loose coupling

#### Usage Guidelines
- **Prefer composition over inheritance**
- **Use Builder for objects with 4+ optional parameters**
- **Apply Strategy for algorithms that change frequently**
- **Implement Observer for event-driven architectures**
- **Use Factory when object creation logic is complex**

## Development Workflow

### Before Writing Code
1. **Architecture First**: Always design the system architecture before implementation
2. **Testing Strategy**: Define test cases and testing approach upfront
3. **Maintainability Review**: Consider code readability, modularity, and future extensibility
4. **Documentation Planning**: Outline what documentation will be needed

### Code Quality Standards
- Write self-documenting code with clear variable and function names
- Follow SOLID principles
- Implement comprehensive error handling
- Use dependency injection where applicable
- Maintain consistent code formatting and style

### Testing Requirements
- **Unit Tests**: Minimum 80% code coverage
- **Integration Tests**: For API endpoints and database operations
- **End-to-End Tests**: For critical user flows
- **Performance Tests**: For high-load scenarios

## Required Documentation

### 1. TODO.md
Track pending tasks, improvements, and known issues

### 2. TASKS.md
Current sprint/iteration tasks with priorities and assignees

### 3. README.md
Project overview, setup instructions, and usage examples

### 4. Additional Documentation
- **API.md**: API documentation with examples
- **ARCHITECTURE.md**: System design and component relationships
- **DEPLOYMENT.md**: Deployment procedures and environment setup
- **CHANGELOG.md**: Version history and release notes

## AI Assistant Behavioral Guidelines

### Problem-Solving Approach
- **Always verify compatibility**: Check language/framework versions before suggesting solutions
- **Research when uncertain**: Use web search for version-specific issues, deprecated methods, or recent changes
- **Provide multiple solutions**: Offer alternatives when possible (e.g., different library versions, alternative approaches)
- **Include version information**: Specify which versions the solution works with

### When to Search for Information
1. **Version Compatibility Issues**: If code doesn't work, check for version conflicts
2. **Deprecated Methods**: Verify if suggested methods are still supported
3. **Recent Framework Changes**: Look up latest documentation for breaking changes
4. **Library Updates**: Check for newer versions with bug fixes
5. **Platform-Specific Issues**: Research OS-specific or environment-specific problems
6. **Security Vulnerabilities**: Check for known security issues in dependencies

### Error Handling Protocol
1. **Analyze the error message**: Break down what the error means
2. **Distinguish Build Phases**: Differentiate between compilation errors (e.g., missing symbols), test failures (e.g., security-related 401/403s), and container build issues (e.g., file not found in build context).
2. **Check common causes**: Version mismatch, missing dependencies, configuration issues
3. **Suggest debugging steps**: Logging, step-by-step verification
4. **Provide fallback solutions**: Alternative approaches if primary solution fails
5. **Research if needed**: Use web search for unfamiliar errors or recent issues

### Code Review Checklist
- **Dependencies**: Are all required packages installed with correct versions?
- **Environment**: Does the code work in the specified environment?
- **Backwards Compatibility**: Will this work with older versions if needed?
- **Forward Compatibility**: Are there any upcoming changes that might break this?
- **Security**: Are there any known vulnerabilities in the suggested approach?

### Response Format for Problems
```
## Problem Analysis
- Error Type: [specific error]
- Likely Cause: [most probable reason]
- Version Check: [relevant version information]

## Solution
[Primary solution with version requirements]

## Alternative Solutions
[Backup approaches if primary fails]

## Verification Steps
[How to confirm the fix works]
```

### Research Triggers
Automatically search for information when encountering:
- Version-specific syntax errors
- Import/dependency errors
- Platform-specific issues
- Recent framework deprecations
- Security-related problems
- Performance optimization queries for specific versions

### Proactive Suggestions
- **Update Notifications**: "This method was deprecated in version X, consider using Y instead"
- **Version Warnings**: "This solution requires Node.js >= 18, your current version might not support it"
- **Security Alerts**: "This library has known vulnerabilities, consider using alternative Z"
- **Performance Tips**: "For better performance in production, consider using X instead of Y"

## Anti-Patterns to Avoid
- **God Object**: Avoid classes that do too much
- **Spaghetti Code**: Avoid complex, tangled control flow
- **Copy-Paste Programming**: Avoid duplicating code blocks
- **Magic Numbers**: Use named constants instead of literal values
- **Premature Optimization**: Don't optimize before profiling
- **Shotgun Surgery**: Avoid changes that require modifications in many places

### Project Organization
```
project/
├── src/
│   ├── main/
│   │   ├── [language-specific-structure]
│   │   └── resources/
│   └── test/
├── docs/
│   ├── README.md
│   ├── TODO.md
│   ├── TASKS.md
│   ├── API.md
│   └── ARCHITECTURE.md
├── scripts/
└── config/
```

### Naming Conventions
- **Classes**: PascalCase (e.g., `UserService`, `DataBuilder`)
- **Functions/Methods**: camelCase (e.g., `getUserById`, `buildConfiguration`)
- **Variables**: camelCase (e.g., `userCount`, `configData`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Files**: kebab-case for scripts, PascalCase for classes

## Error Handling Strategy

### Logging Levels
- **ERROR**: System failures, exceptions
- **WARN**: Potential issues, deprecated usage
- **INFO**: General application flow
- **DEBUG**: Detailed diagnostic information

### Exception Handling
- Use specific exception types
- Always log errors with context
- Implement graceful degradation
- Provide meaningful error messages to users

## Performance Considerations

### Optimization Priorities
1. **Database Queries**: Use indexing, avoid N+1 problems
2. **Memory Management**: Implement proper garbage collection strategies
3. **Caching**: Redis/Memcached for frequently accessed data
4. **Asynchronous Processing**: For I/O-bound operations

### Monitoring Requirements
- Application metrics (response time, throughput)
- System metrics (CPU, memory, disk usage)
- Business metrics (user activity, feature usage)
- Error rates and exception tracking

## Security Guidelines

### Data Protection
- Encrypt sensitive data at rest and in transit
- Use parameterized queries to prevent SQL injection
- Implement proper authentication and authorization
- Validate all input data
- Use environment variables for secrets

### Code Security
- Regular dependency updates
- Static code analysis
- Security testing in CI/CD pipeline
- Follow OWASP guidelines

## Development Environment

### Required Tools
- **Version Control**: Git with conventional commits
- **Code Formatting**: Language-specific formatters (Prettier, Black, gofmt)
- **Linting**: ESLint, Pylint, golangci-lint, CheckStyle
- **Package Management**: npm/yarn, pip/poetry, go mod, Maven/Gradle

### CI/CD Pipeline
1. **Lint**: Code style and quality checks
2. **Test**: Run all test suites
3. **Build**: Compile and package application
4. **Security Scan**: Dependency and code vulnerability checks
5. **Deploy**: Automated deployment to staging/production

## Communication Preferences

### Code Reviews
- All code must be reviewed before merging
- Focus on logic, performance, and maintainability
- Suggest improvements, not just point out issues
- Include test coverage in review criteria

### Commit Messages
Follow conventional commits:
```
type(scope): description

[optional body]

[optional footer]
```

Types: feat, fix, docs, style, refactor, test, chore

### Pull Request Template
- Description of changes
- Testing approach
- Breaking changes (if any)
- Documentation updates
- Performance impact

## Additional Guidelines

### Code Comments
- Explain "why" not "what"
- Document complex algorithms
- Include examples for public APIs
- Keep comments up-to-date with code changes

### Dependency Management
- Minimize external dependencies
- Prefer well-maintained, popular libraries
- Document reasons for dependency choices
- Regular security updates

### Deployment Strategy
- Blue-green deployment for zero downtime
- Feature flags for gradual rollouts
- Rollback procedures documented
- Database migration strategies

---

*This document should be updated as the project evolves and new requirements emerge.*