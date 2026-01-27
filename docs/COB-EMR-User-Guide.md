# COB EMR Service - User Guide

## Overview

The COB EMR (Electronic Medical Records) Service is a comprehensive healthcare management system designed to streamline clinic operations, patient management, and medical documentation. This guide provides an overview of the key features and functionalities available to end users.

---

## Table of Contents

1. [Organization Management](#1-organization-management)
2. [Clinic Management](#2-clinic-management)
3. [Patient Profile](#3-patient-profile)
4. [Patient Chart](#4-patient-chart)
5. [Authorization Management](#5-authorization-management)
6. [Scheduler and Calendar](#6-scheduler-and-calendar)
7. [Patient Appointments](#7-patient-appointments)

---

## 1. Organization Management

### What is an Organization?

An Organization represents the top-level entity in the COB EMR system. It typically represents a healthcare group, medical practice, or healthcare network that may operate multiple clinics.

### Key Features

| Feature | Description |
|---------|-------------|
| **Organization Profile** | Store essential business information including organization name, DBA (Doing Business As), Group NPI, and Tax ID |
| **Billing Address** | Maintain the official billing address for the organization |
| **Multi-Clinic Support** | Manage multiple clinics under a single organization |
| **User Administration** | Assign administrative users to manage organization settings |

### How It Works

1. **Create Organization**: Set up your organization with basic information
2. **Configure Details**: Add billing address and tax information
3. **Add Clinics**: Create and assign clinics to your organization
4. **Manage Users**: Assign clinical and clerical staff to the organization

---

## 2. Clinic Management

### What is a Clinic?

A Clinic represents an individual healthcare facility or location within an organization. Each clinic operates independently while being part of the parent organization.

### Key Features

| Feature | Description |
|---------|-------------|
| **Clinic Profile** | Store clinic name and location details |
| **Address Management** | Maintain clinic address information |
| **Staff Assignment** | Assign clinical and clerical users to specific clinics |
| **Patient Roster** | Manage the list of patients associated with each clinic |
| **Scheduler Configuration** | Configure operating hours and scheduling settings |

### User Types

- **Clinical Users**: Healthcare providers (doctors, therapists, nurses) who provide patient care
- **Clerical Users**: Administrative staff who manage appointments, billing, and front-desk operations

### How It Works

1. **Create Clinic**: Add a new clinic under your organization
2. **Set Location**: Configure the clinic address
3. **Assign Staff**: Add clinical and clerical users to the clinic
4. **Register Patients**: Associate patients with the clinic
5. **Configure Scheduler**: Set up operating hours and calendar settings

---

## 3. Patient Profile

### What is a Patient Profile?

The Patient Profile contains comprehensive demographic, contact, and insurance information for each patient. It serves as the central repository for all patient-related data.

### Key Features

| Feature | Description |
|---------|-------------|
| **Demographics** | First name, middle name, last name, date of birth, gender, marital status |
| **Identification** | Patient ID, ID type, effective dates |
| **Contact Information** | Multiple addresses and phone numbers |
| **Emergency Contacts** | Emergency contact details for patient safety |
| **Insurance Information** | Multiple insurance policies with coverage details |
| **Employment** | Employer information |
| **Dependents** | Dependent family member information |

### Patient Information Sections

#### Personal Information
- Title (Mr., Mrs., Dr., etc.)
- First Name, Middle Name, Last Name
- Suffix (Jr., Sr., III, etc.)
- Date of Birth
- Gender
- Marital Status

#### Contact Details
- Primary Address
- Secondary Address
- Phone Numbers (Home, Mobile, Work)
- Email Address

#### Insurance Coverage
- Primary Insurance
- Secondary Insurance
- Policy Numbers
- Group Numbers
- Coverage Dates

#### Emergency Information
- Emergency Contact Name
- Relationship
- Phone Number

### How It Works

1. **Create Patient**: Enter basic demographic information
2. **Add Contact Info**: Input addresses and phone numbers
3. **Enter Insurance**: Add insurance policy details
4. **Assign to Clinic**: Associate patient with one or more clinics
5. **Create Cases**: Set up patient cases for treatment tracking

---

## 4. Patient Chart

### What is a Patient Chart?

The Patient Chart is the digital medical record that contains all clinical documentation for a patient. It includes medical notes, assessments, treatment plans, and appointment history.

### Key Features

| Feature | Description |
|---------|-------------|
| **Medical Notes** | Create and manage various types of clinical documentation |
| **Appointment History** | View past, upcoming, cancelled, and no-show appointments |
| **Treatment Plans** | Document and track patient treatment plans |
| **Assessments** | Record clinical assessments and findings |
| **Visit Tracking** | Monitor visit numbers and service dates |

### Medical Note Types

| Note Type | Purpose |
|-----------|---------|
| **Initial Examination** | Document the first comprehensive evaluation of a patient |
| **SOAP Notes** | Record Subjective, Objective, Assessment, and Plan for each visit |
| **Daily Notes** | Document routine daily encounters |
| **OMT Notes** | Record Osteopathic Manipulative Treatment details |
| **Discharge Notes** | Document patient discharge and summary of care |

### SOAP Note Components

- **Subjective (S)**: Patient's reported symptoms, complaints, and history
- **Objective (O)**: Clinical findings, vital signs, examination results
- **Assessment (A)**: Diagnosis, clinical impression, problem list
- **Plan (P)**: Treatment plan, medications, follow-up instructions

### Chart Views

| View | Description |
|------|-------------|
| **Incoming Appointments** | Upcoming scheduled appointments |
| **Previous Appointments** | Completed past appointments |
| **Cancelled Appointments** | Appointments that were cancelled |
| **No-Show History** | Appointments where patient did not attend |

### How It Works

1. **Access Patient Chart**: Select a patient and open their chart
2. **Create Notes**: Document visits using appropriate note types
3. **Review History**: View past appointments and notes
4. **Finalize Documentation**: Complete and sign off on medical notes
5. **Track Progress**: Monitor patient treatment progress over time

---

## 5. Authorization Management

### What is Authorization?

Authorization refers to the approval from insurance companies for specific medical treatments or services. Managing authorizations ensures that services are covered and claims are processed correctly.

### Key Features

| Feature | Description |
|---------|-------------|
| **Authorization Tracking** | Record and monitor insurance authorizations |
| **Validity Dates** | Track effective start and end dates |
| **Insurance Linkage** | Associate authorizations with specific insurance policies |
| **Visit Limits** | Monitor authorized number of visits |

### Authorization Information

| Field | Description |
|-------|-------------|
| **Authorization Name** | Descriptive name for the authorization |
| **Authorization Type** | Type of authorization (e.g., Physical Therapy, Consultation) |
| **Authorization Number** | Reference number from insurance company |
| **Effective Start Date** | When the authorization becomes valid |
| **Effective End Date** | When the authorization expires |
| **Insurance Name** | The associated insurance company |

### How It Works

1. **Obtain Authorization**: Get approval from insurance company
2. **Record Details**: Enter authorization information in the system
3. **Link to Patient Case**: Associate authorization with patient's treatment case
4. **Monitor Usage**: Track visits against authorized limits
5. **Renew as Needed**: Request new authorizations before expiration

---

## 6. Scheduler and Calendar

### What is the Scheduler?

The Scheduler is the appointment management system that allows clinics to configure their operating hours, manage calendars, and control how appointments are scheduled.

### Key Features

| Feature | Description |
|---------|-------------|
| **Operating Hours** | Set clinic start and end times |
| **Multiple Calendars** | Create and manage multiple calendars per clinic |
| **Calendar Visibility** | Configure public or private calendars |
| **User Preferences** | Personalize calendar views and settings |
| **Calendar Attachment** | Assign calendars to specific users or clinics |

### Scheduler Configuration

| Setting | Description |
|---------|-------------|
| **Start Hour** | Clinic opening time |
| **End Hour** | Clinic closing time |
| **Clinic Assignment** | Which clinic the configuration applies to |

### Calendar Types

- **Public Calendars**: Visible to all staff members in the clinic
- **Private Calendars**: Visible only to specific assigned users

### How It Works

1. **Configure Scheduler**: Set operating hours for your clinic
2. **Create Calendars**: Add calendars for different purposes or providers
3. **Set Visibility**: Make calendars public or private
4. **Attach Users**: Assign calendars to specific staff members
5. **Personalize View**: Users select which calendars to display

---

## 7. Patient Appointments

### What is an Appointment?

An Appointment represents a scheduled visit between a patient and a healthcare provider. The appointment system manages booking, tracking, and documenting patient visits.

### Key Features

| Feature | Description |
|---------|-------------|
| **Single Appointments** | Book individual one-time appointments |
| **Recurring Appointments** | Schedule repeating appointments (daily, weekly, monthly, yearly) |
| **Status Tracking** | Monitor appointment status throughout lifecycle |
| **Appointment Types** | Categorize appointments by type of service |
| **Status History** | Audit trail of all status changes |
| **Cancel/No-Show Tracking** | Document reasons for cancellations and no-shows |

### Appointment Status

| Status | Description |
|--------|-------------|
| **Scheduled** | Appointment has been booked |
| **Confirmed** | Patient has confirmed attendance |
| **Checked In** | Patient has arrived at the clinic |
| **In Progress** | Patient is being seen |
| **Completed** | Appointment has been finished |
| **Cancelled** | Appointment was cancelled |
| **No-Show** | Patient did not attend |

### Recurring Appointment Patterns

| Pattern | Description |
|---------|-------------|
| **Daily** | Repeats every day or every X days |
| **Weekly** | Repeats on selected days of the week |
| **Monthly** | Repeats on a specific day each month |
| **Yearly** | Repeats annually on a specific date |

### Appointment Information

- **Patient**: Who the appointment is for
- **Patient Case**: Which treatment case the visit relates to
- **Provider/Therapist**: Who will see the patient
- **Calendar**: Which calendar the appointment appears on
- **Date & Time**: When the appointment is scheduled
- **Duration**: Length of the appointment
- **Type**: Category of appointment
- **Notes**: Additional instructions or comments

### How It Works

1. **Select Patient**: Choose the patient for the appointment
2. **Choose Time**: Select available date and time slot
3. **Set Type**: Choose the appointment type
4. **Add Details**: Include notes or special instructions
5. **Book Appointment**: Confirm and save the appointment
6. **Track Status**: Update status as appointment progresses
7. **Document Outcome**: Record if cancelled or no-show with reason

---

## Quick Reference

### Navigation Flow

```
Organization
    |
    +-- Clinic(s)
            |
            +-- Staff (Clinical & Clerical Users)
            |
            +-- Patients
            |       |
            |       +-- Patient Profile
            |       |
            |       +-- Patient Cases
            |               |
            |               +-- Authorizations
            |               |
            |               +-- Patient Chart
            |                       |
            |                       +-- Medical Notes
            |
            +-- Scheduler Configuration
                    |
                    +-- Calendars
                            |
                            +-- Appointments
```

### Common Tasks

| Task | Steps |
|------|-------|
| Register New Patient | Patient Profile > Create Patient > Enter Demographics > Add Insurance |
| Schedule Appointment | Scheduler > Select Calendar > Choose Date/Time > Select Patient > Book |
| Create Medical Note | Patient Chart > Select Note Type > Document Visit > Finalize |
| Add Authorization | Patient > Select Case > Authorization > Add New Authorization |
| View Patient History | Patient Chart > Previous Appointments / Medical Notes |

---

## Support

For technical support or questions about using the COB EMR Service, please contact your system administrator or IT support team.

---

*Document Version: 1.0*
*Last Updated: January 2026*
