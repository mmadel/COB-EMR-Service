@echo off
REM EMR Package Structure Generator for Windows
REM Run this from: src\main\java\

set BASE_PATH=com\cob\emr\ddd

echo Creating EMR package structure...

REM ============================================================
REM DOMAIN LAYER
REM ============================================================
echo Creating Domain layer...

mkdir "%BASE_PATH%\domain\medicalnote\model\subjective" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\model\objective" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\model\assessment" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\model\planofcare" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\model\billing" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\model\valueobjects" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\service" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\repository" 2>nul
mkdir "%BASE_PATH%\domain\medicalnote\exception" 2>nul

mkdir "%BASE_PATH%\domain\patientcase\model" 2>nul
mkdir "%BASE_PATH%\domain\patientcase\service" 2>nul
mkdir "%BASE_PATH%\domain\patientcase\repository" 2>nul

mkdir "%BASE_PATH%\domain\patient\model" 2>nul
mkdir "%BASE_PATH%\domain\patient\repository" 2>nul

REM ============================================================
REM APPLICATION LAYER
REM ============================================================
echo Creating Application layer...

mkdir "%BASE_PATH%\application\medicalnote\usecase" 2>nul
mkdir "%BASE_PATH%\application\medicalnote\service" 2>nul
mkdir "%BASE_PATH%\application\medicalnote\dto" 2>nul
mkdir "%BASE_PATH%\application\common\validation" 2>nul

REM ============================================================
REM INFRASTRUCTURE LAYER
REM ============================================================
echo Creating Infrastructure layer...

mkdir "%BASE_PATH%\infrastructure\persistence\entity" 2>nul
mkdir "%BASE_PATH%\infrastructure\persistence\repository" 2>nul
mkdir "%BASE_PATH%\infrastructure\persistence\mapper" 2>nul
mkdir "%BASE_PATH%\infrastructure\external\fhir" 2>nul
mkdir "%BASE_PATH%\infrastructure\external\hl7" 2>nul
mkdir "%BASE_PATH%\infrastructure\config" 2>nul

REM ============================================================
REM ADAPTER LAYER
REM ============================================================
echo Creating Adapter layer...

mkdir "%BASE_PATH%\adapter\rest\medicalnote" 2>nul
mkdir "%BASE_PATH%\adapter\rest\patient" 2>nul
mkdir "%BASE_PATH%\adapter\dto\request" 2>nul
mkdir "%BASE_PATH%\adapter\dto\response" 2>nul
mkdir "%BASE_PATH%\adapter\mapper" 2>nul
mkdir "%BASE_PATH%\adapter\exception" 2>nul

REM ============================================================
REM CONFIG & COMMON
REM ============================================================
echo Creating Config and Common...

mkdir "%BASE_PATH%\config" 2>nul
mkdir "%BASE_PATH%\common\util" 2>nul
mkdir "%BASE_PATH%\common\constant" 2>nul
mkdir "%BASE_PATH%\common\validation" 2>nul

REM ============================================================
REM CREATE PLACEHOLDER FILES
REM ============================================================
echo Creating placeholder files...

REM Domain Models
type nul > "%BASE_PATH%\domain\medicalnote\model\MedicalNote.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\InitialEvaluation.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\DailyNote.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\ProgressNote.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\FullDischargeNote.java"

REM Value Objects
type nul > "%BASE_PATH%\domain\medicalnote\model\valueobjects\NoteType.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\valueobjects\NoteStatus.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\valueobjects\VisitType.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\valueobjects\WorkStatus.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\valueobjects\ActivityLevel.java"

REM Subjective Models
type nul > "%BASE_PATH%\domain\medicalnote\model\subjective\SubjectiveFindings.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\subjective\ExaminationContext.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\subjective\ChiefComplaint.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\subjective\PainAssessment.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\subjective\MedicalHistory.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\subjective\FunctionalAssessment.java"

REM Assessment Models
type nul > "%BASE_PATH%\domain\medicalnote\model\assessment\ClinicalAssessment.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\assessment\FunctionalGoal.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\assessment\Problem.java"

REM Plan of Care Models
type nul > "%BASE_PATH%\domain\medicalnote\model\planofcare\PlanOfCare.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\planofcare\TreatmentPlan.java"

REM Billing Models
type nul > "%BASE_PATH%\domain\medicalnote\model\billing\BillingInformation.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\billing\TimedCode.java"
type nul > "%BASE_PATH%\domain\medicalnote\model\billing\UntimedCode.java"

REM Objective Models
type nul > "%BASE_PATH%\domain\medicalnote\model\objective\ObjectiveFindings.java"

REM Domain Services
type nul > "%BASE_PATH%\domain\medicalnote\service\NoteInheritanceService.java"
type nul > "%BASE_PATH%\domain\medicalnote\service\VisitManagementService.java"
type nul > "%BASE_PATH%\domain\medicalnote\service\GoalTrackingService.java"
type nul > "%BASE_PATH%\domain\medicalnote\service\BillingValidationService.java"

REM Repository Interface
type nul > "%BASE_PATH%\domain\medicalnote\repository\MedicalNoteRepository.java"

REM Application Services
type nul > "%BASE_PATH%\application\medicalnote\service\InitialEvaluationService.java"
type nul > "%BASE_PATH%\application\medicalnote\service\DailyNoteService.java"
type nul > "%BASE_PATH%\application\medicalnote\service\ProgressNoteService.java"

REM Infrastructure
type nul > "%BASE_PATH%\infrastructure\persistence\entity\MedicalNoteEntity.java"
type nul > "%BASE_PATH%\infrastructure\persistence\repository\MedicalNoteRepositoryImpl.java"
type nul > "%BASE_PATH%\infrastructure\persistence\repository\JpaMedicalNoteRepository.java"

REM Controllers
type nul > "%BASE_PATH%\adapter\rest\medicalnote\InitialEvaluationController.java"
type nul > "%BASE_PATH%\adapter\rest\medicalnote\DailyNoteController.java"
type nul > "%BASE_PATH%\adapter\rest\medicalnote\ProgressNoteController.java"

REM DTOs
type nul > "%BASE_PATH%\adapter\dto\request\InitialEvaluationRequestDTO.java"
type nul > "%BASE_PATH%\adapter\dto\request\SubjectiveRequestDTO.java"
type nul > "%BASE_PATH%\adapter\dto\response\InitialEvaluationResponseDTO.java"

REM Mappers
type nul > "%BASE_PATH%\adapter\mapper\InitialEvaluationDtoMapper.java"
type nul > "%BASE_PATH%\infrastructure\persistence\mapper\MedicalNoteEntityMapper.java"

echo.
echo Package structure created successfully!
echo.
echo Structure created at: %BASE_PATH%
echo.
echo Next steps:
echo 1. Refresh your IntelliJ project (File - Reload All from Disk)
echo 2. Mark src\main\java as 'Sources Root' if not already
echo 3. Start adding your domain model code!
echo.
echo Happy coding!

pause