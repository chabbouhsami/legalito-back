truncate table template;

INSERT INTO template (title, slug, content_template, description, language, created_at)
VALUES 
('Lettre de résiliation', 'resiliation', 'Bonjour, je souhaite résilier mon contrat n°${num_contrat}.', 'Pour mettre fin à un contrat ou un abonnement', 'fr', NOW()),

('Lettre de mise en demeure', 'mise-en-demeure', 'Par la présente, je vous mets en demeure de respecter vos engagements...', 'Pour exiger le respect de vos droits', 'fr', NOW()),

('Lettre de contestation', 'contestation', 'Je conteste la décision suivante : ${decision}.', 'Pour contester une décision ou une facture', 'fr', NOW()),

('Lettre de réclamation', 'reclamation', 'Je souhaite porter réclamation concernant ${objet}.', 'Pour formuler une plainte ou une demande d''indemnisation', 'fr', NOW());
