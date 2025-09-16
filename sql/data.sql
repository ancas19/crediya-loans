-- Example for estado
INSERT INTO estado (name, descripcion, u_creacion, u_actualizacion, f_creacion, f_actualizacion)
VALUES
  ('APROBADO', 'Estado aprobado', 'ADMIN', null, NOW(),NULL),
  ('PENDIENTE', 'Pendiente revisión', 'ADMIN', null, NOW(),NULL),
  ('RECHAZADO', 'Estado rechazado', 'ADMIN', NULL, NOW() ,NULL);

-- Example for tipo_prestamo
INSERT INTO tipo_prestamo (nombre, monto_minimo, monto_maximo, tasa_interes, validacion_automatica, u_creacion, u_actualizacion, f_creacion, f_actualizacion)
VALUES
  ('PERSONAL', 50000, 250000, 5.5, 'SI', 'ADMIN', NULL, NOW(), NULL),
  ('EMPRENDIMIENTO', 3000000, 10000000, 5.5, 'SI', 'ADMIN', NULL, NOW(), NULL),
  ('CONSUMO', 500000, 2500000, 3.2, 'NO', 'ADMIN', NULL, NOW(), NULL);

-- Example for correos_mensaje
INSERT INTO correos_mensaje
  (nombre, subject, content, u_creacion, u_actualizacion, f_creacion, f_actualizacion)
VALUES
(
  'RECHAZADO',
  'Crédito rechazado',
  $$
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Loan Approved</title>
</head>
<body style="margin:0;padding:0;background-color:#f4f6f8;font-family:Arial,Helvetica,sans-serif;">
  <table role="presentation" cellpadding="0" cellspacing="0" width="100%" style="max-width:600px;margin:32px auto;background:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 2px 6px rgba(0,0,0,0.08)">

    <!-- Header -->
    <tr>
      <td style="padding:24px;text-align:center;background:linear-gradient(90deg,#0ea5a4,#06b6d4);color:#ffffff;">
        <h1 style="margin:0;font-size:20px;font-weight:600;">Loan Approved 🎉</h1>
        <p style="margin:6px 0 0;font-size:14px;opacity:0.95;">Congratulations, {{name}}!</p>
      </td>
    </tr>

    <!-- Body -->
    <tr>
      <td style="padding:20px;">
        <p style="margin:0 0 12px;font-size:15px;color:#0b2b34;">
          We're happy to let you know your loan application has been <strong>approved</strong>.
        </p>

        <!-- Loan details box -->
        <table role="presentation" cellpadding="0" cellspacing="0" width="100%" style="border:1px solid #e6eef0;border-radius:6px;">
          <tr>
            <td style="padding:14px;">
              <p style="margin:0;font-size:13px;color:#333;"><strong>Loan ID:</strong> {{loanId}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Amount:</strong> {{amount}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Term:</strong> {{term}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Monthly payment:</strong> {{monthlyPayment}}</p>
            </td>
          </tr>
        </table>

        <p style="margin:16px 0 18px;font-size:14px;color:#0b2b34;">
          To accept the loan and view the contract, click the button below. If you have questions, reply to this email or visit your dashboard.
        </p>

        <!-- Payments Table -->
        <table role="presentation" cellpadding="0" cellspacing="0" width="100%" style="border-collapse:collapse;width:100%;margin-bottom:18px;">
          <thead>
            <tr style="background-color:#f4f6f8;">
              <th style="padding:8px;font-size:13px;text-align:left;border:1px solid #e6eef0;">ID</th>
              <th style="padding:8px;font-size:13px;text-align:left;border:1px solid #e6eef0;">Amount</th>
              <th style="padding:8px;font-size:13px;text-align:left;border:1px solid #e6eef0;">Interest</th>
              <th style="padding:8px;font-size:13px;text-align:left;border:1px solid #e6eef0;">Capital</th>
              <th style="padding:8px;font-size:13px;text-align:left;border:1px solid #e6eef0;">Balance</th>
            </tr>
          </thead>
          <tbody>
                {{content}}
          </tbody>
        </table>

        <hr style="border:none;border-top:1px solid #eef3f4;margin:18px 0;">

        <p style="margin:0;font-size:12px;color:#718089;">
          If you didn't apply for this loan, contact us immediately at
          <a href="mailto:support@crediya.com" style="color:#06b6d4;text-decoration:none;">support@crediya.com</a>.
        </p>
      </td>
    </tr>

    <!-- Footer -->
    <tr>
      <td style="padding:14px;background:#fbfdfe;text-align:center;font-size:12px;color:#94a3a8;">
        © {{year}} crediya •
      </td>
    </tr>

  </table>
</body>
</html>
$$,
  'ADMIN',
  NULL,
  NOW(),
  NULL
);


INSERT INTO correos_mensaje
  (nombre, subject, content, u_creacion, u_actualizacion, f_creacion, f_actualizacion)
VALUES
(
  'APROBADO',
  'Crédito aprobado',
  $$
<!-- Loan Rejected Email -->
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Loan Decision</title>
</head>
<body style="margin:0;padding:0;background-color:#f4f6f8;font-family:Arial,Helvetica,sans-serif;">
  <table role="presentation" cellpadding="0" cellspacing="0" width="100%" style="max-width:600px;margin:32px auto;background:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 2px 6px rgba(0,0,0,0.06)">
    <tr>
      <td style="padding:24px;text-align:center;background:#fff1f2;color:#7f1d1d;">
        <h1 style="margin:0;font-size:20px;">Loan Application Update</h1>
        <p style="margin:6px 0 0;font-size:14px;opacity:0.95;">Hi {{name}}</p>
      </td>
    </tr>

    <tr>
      <td style="padding:20px;">
        <p style="margin:0 0 12px;font-size:15px;color:#1f2937;">
          We reviewed your application and, unfortunately, we are unable to approve your loan at this time.
        </p>

          <table role="presentation" cellpadding="0" cellspacing="0" width="100%" style="border:1px solid #e6eef0;border-radius:6px;">
          <tr>
            <td style="padding:14px;">
              <p style="margin:0;font-size:13px;color:#333;"><strong>Loan ID:</strong> {{loanId}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Amount:</strong> {{amount}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Term:</strong> {{term}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Monthly payment:</strong> {{monthlyPayment}}</p>
            </td>
          </tr>
        </table>

        <p style="margin:16px 0 12px;font-size:14px;color:#111827;">
          Common reasons include insufficient credit history, debt-to-income ratio, or missing documents. We recommend reviewing your application details and trying again.
        </p>

        <ul style="padding-left:18px;margin:6px 0 16px;color:#111827;font-size:14px;">
          <li>Check your credit report and correct any errors</li>
          <li>Ensure all required documents were uploaded</li>
          <li>Reduce outstanding debts</li>
        </ul>

        <p style="margin:0 0 18px;font-size:14px;color:#111827;">
          If you'd like personalized guidance, click below to contact our support team or see suggestions to improve eligibility.
        </p>


        <hr style="border:none;border-top:1px solid #faf0f1;margin:18px 0;">

        <p style="margin:0;font-size:12px;color:#94a3a8;">
          We understand this may be disappointing. If you want to reapply later, we'll be here to help.
        </p>
      </td>
    </tr>

    <tr>
      <td style="padding:14px;background:#fbfdfe;text-align:center;font-size:12px;color:#94a3a8;">
        © {{year}} Crediya •
      </td>
    </tr>
  </table>
</body>
</html>

$$,
  'ADMIN',
  NULL,
  NOW(),
  NULL
);



INSERT INTO correos_mensaje
  (nombre, subject, content, u_creacion, u_actualizacion, f_creacion, f_actualizacion)
VALUES
(
  'PENDIENTE',
  'Crédito pendiente por aprobación',
  $$

<!-- Loan Pending Email -->
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Loan Pending</title>
</head>
<body style="margin:0;padding:0;background-color:#f4f6f8;font-family:Arial,Helvetica,sans-serif;">
  <table role="presentation" cellpadding="0" cellspacing="0" width="100%" style="max-width:600px;margin:32px auto;background:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 2px 6px rgba(0,0,0,0.08)">
    <!-- Header -->
    <tr>
      <td style="padding:24px;text-align:center;background:linear-gradient(90deg,#f59e0b,#fbbf24);color:#ffffff;">
        <h1 style="margin:0;font-size:20px;">Loan Application Pending ⏳</h1>
        <p style="margin:6px 0 0;font-size:14px;opacity:0.95;">Hello, {{name}}</p>
      </td>
    </tr>

    <!-- Body -->
    <tr>
      <td style="padding:20px;">
        <p style="margin:0 0 12px;font-size:15px;color:#0b2b34;">
          Your loan application is currently <strong>under review</strong>. We’re verifying your information and will notify you once a decision has been made.
        </p>

        <!-- Loan details box -->
            <table role="presentation" cellpadding="0" cellspacing="0" width="100%" style="border:1px solid #e6eef0;border-radius:6px;">
          <tr>
            <td style="padding:14px;">
              <p style="margin:0;font-size:13px;color:#333;"><strong>Loan ID:</strong> {{loanId}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Amount:</strong> {{amount}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Term:</strong> {{term}}</p>
              <p style="margin:6px 0 0;font-size:13px;color:#333;"><strong>Monthly payment:</strong> {{monthlyPayment}}</p>
            </td>
          </tr>
        </table>

        <p style="margin:16px 0 18px;font-size:14px;color:#0b2b34;">
          We’ll send you an email when your loan is approved or if we need additional information. You can also check the status of your application in your dashboard.
        </p>

        <p style="text-align:center;margin:0 0 18px;">
          <a href="{{statusUrl}}" style="display:inline-block;padding:12px 20px;border-radius:6px;background:#f59e0b;color:white;text-decoration:none;font-weight:600;">
            View application status
          </a>
        </p>

        <hr style="border:none;border-top:1px solid #eef3f4;margin:18px 0;">

        <p style="margin:0;font-size:12px;color:#718089;">
          If you didn’t apply for this loan, contact us immediately at
          <a href="mailto:support@example.com" style="color:#06b6d4;text-decoration:none;">support@example.com</a>.
        </p>
      </td>
    </tr>

    <!-- Footer -->
    <tr>
      <td style="padding:14px;background:#fbfdfe;text-align:center;font-size:12px;color:#94a3a8;">
        © {{year}} Your Company • <a href="{{privacyUrl}}" style="color:#06b6d4;text-decoration:none;">Privacy</a> • <a href="{{helpUrl}}" style="color:#06b6d4;text-decoration:none;">Help</a>
      </td>
    </tr>
  </table>
</body>
</html>


$$,
  'ADMIN',
  NULL,
  NOW(),
  NULL
);


