prompt = 'Enter 1 to do Single-Team Win Probability, or 2 to do Head-to-Head \n';
mode = input(prompt,'s');
% Single team win probability
x = 60:1:160; % Set bounds and accuracy for x axis
if mode == '1'
    while 1
        prompt = 'Please enter a team name \n';
        team = input(prompt, 's');
        team = lower(team);
        for row=1:1:30
            if team == lower(data(row,1))
                % Creates pdfs for Points Scored and Points Against PG
                PPGMean = str2double(data(row,2)); % stores value from table
                PAPGMean = str2double(data(row,3));
                PPGStdDev = str2double(data(row,4));
                PAPGStdDev = str2double(data(row,5));
                PPG = normpdf(x,PPGMean,PPGStdDev); % creates Gaussian for PPG
                PAPG = normpdf(x,PAPGMean,PAPGStdDev); % creates Guassian for PAPG
                figure;
                plot(x,PPG,x,PAPG,'--') % plots both graphs on top of each other
                legend('Points Scored','Points Allowed') % add a legend
                title(strcat(data(row,1),' Statistics')) % add a title
                % Finds probability of winning any given game
                testMean = PPGMean-PAPGMean;
                testStdDev = sqrt((PPGStdDev^2)+(PAPGStdDev^2));
                prob = 1 - normcdf(0,testMean,testStdDev);
                fprintf(strcat('Chance to win any given game: \n',num2str(prob)));
                fprintf('\n\n');
                return
            end
        end
        fprintf('Failure! Try a new team\n\n')
    end
% Head-to-Head win probability
elseif mode == '2'
    valid1 = 0;
    valid2 = 0;
    while 1
        if ~valid1
            prompt = 'Please enter Team 1 \n';
            team = input(prompt, 's');
            team = lower(team);
            for row=1:1:30
                if team == lower(data(row,1))
                    team = data(row,1);
                    % Creates pdfs for Points Scored and Points Against PG
                    PPGMean1 = str2double(data(row,2)); % stores value from table
                    PAPGMean1 = str2double(data(row,3));
                    PPGStdDev1 = str2double(data(row,4));
                    PAPGStdDev1 = str2double(data(row,5));
                    valid1 = 1;
                end
            end
        end
        if ~valid2
            prompt = 'Please enter Team 2 \n';
            team2 = input(prompt,'s');
            team2 = lower(team2);
            for row=1:1:30
                if team2 == lower(data(row,1))
                    team2 = data(row,1);
                    % Creates pdfs for Points Scored and Points Against PG
                    PPGMean2 = str2double(data(row,2)); % stores value from table
                    PAPGMean2 = str2double(data(row,3));
                    PPGStdDev2 = str2double(data(row,4));
                    PAPGStdDev2 = str2double(data(row,5));
                    valid2 = 1;
                end
            end
        end
        if valid1 && valid2
            PointsScored1 = (PPGMean1+PAPGMean2)/2;
            StdDev1 = sqrt((PPGStdDev1^2)+(PAPGStdDev2^2))/2;
            team1Points = normpdf(x,PointsScored1,StdDev1); % creates Gaussian for Team1
            PointsScored2 = (PPGMean2+PAPGMean1)/2;
            StdDev2 = sqrt((PPGStdDev2^2)+(PAPGStdDev1^2))/2;
            team2Points = normpdf(x,PointsScored2,StdDev2); % creates Gaussian for Team2
            figure;
            plot(x,team1Points,x,team2Points,'--') % plots both graphs on top of each other
            legend(strcat(team,' Points'),strcat(team2,' Points')) % add a legend
            title('Head to Head') % add a title
            testMean = PointsScored1-PointsScored2;
            testStdDev = sqrt((StdDev1^2)+(StdDev2^2));
            prob = 1 - normcdf(0,testMean,testStdDev);
            fprintf(strcat('Probability that the',{' '},team,' beat the',{' '},team2, ': \n',num2str(prob)));
            fprintf('\n\n');
            return
        else
            fprintf('Please enter a valid team name! \n');
        end
    end
end